package app.com.example.shalan.bakingudacity;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import app.com.example.shalan.bakingudacity.Model.Recipe;

/**
 * Created by noura on 09/07/2017.
 */

public class RecipeWidgetService extends IntentService {

    public static final String RECIPE_ACTION_UPDATE =
            "app.com.example.shalan.bakingudacity.action.update" ;


    public RecipeWidgetService() {
        super("RecipeWidgetService");
    }

    public static void startActinoUpdateWidget(Context context){
        Intent intent = new Intent(context,RecipeWidgetService.class);
        intent.setAction(RECIPE_ACTION_UPDATE);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (RECIPE_ACTION_UPDATE.equals(action)) {
                handleActinoUpdateWidget((Recipe) intent.getSerializableExtra("recipe"));
            }
        }
    }

    private void handleActinoUpdateWidget(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateRecipeWidget(this,recipe,appWidgetManager,appWidgetIds);

    }
}
