package org.inventivetalent.recipebuilder;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class RecipeBuilderPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		new Metrics(this);
	}
}
