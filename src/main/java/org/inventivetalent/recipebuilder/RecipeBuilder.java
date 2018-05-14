package org.inventivetalent.recipebuilder;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.inventivetalent.itembuilder.ItemBuilder;

public abstract class RecipeBuilder {

	protected Recipe recipe;

	protected RecipeBuilder() {
	}

	protected RecipeBuilder(ItemStack result) {
		forResult(result);
	}

	@Deprecated
	protected abstract void initRecipe(ItemStack result);

	protected abstract void initRecipe(NamespacedKey key, ItemStack result);

	protected void validateInit() {
		if (recipe == null) { throw new IllegalStateException("Recipe not yet initiated"); }
	}

	/**
	 * Loads the recipe from a {@link ConfigurationSection} See <link>https://paste.inventivetalent.org/irorulawev.yml</link> for an example configuration
	 *
	 * @param section {@link ConfigurationSection}
	 * @return the RecipeBuilder
	 */
	public RecipeBuilder fromConfig(ConfigurationSection section) {
		if (section.contains("result")) {
			forResult(new ItemBuilder().fromConfig(section.getConfigurationSection("result")).build());
		}
		return this;
	}

	/**
	 * Set the recipe result
	 *
	 * @param result {@link ItemStack}
	 * @return the RecipeBuilder
	 * @deprecated use {@link #forResult(NamespacedKey, ItemStack)}
	 */
	@Deprecated
	public RecipeBuilder forResult(ItemStack result) {
		initRecipe(result);
		return this;
	}

	/**
	 * Set the recipe result
	 *
	 * @param result {@link ItemStack}
	 * @return the RecipeBuilder
	 */
	public RecipeBuilder forResult(NamespacedKey key, ItemStack result) {
		initRecipe(key, result);
		return this;
	}

	/**
	 * Builds the recipe
	 *
	 * @return the built {@link Recipe}
	 */
	public Recipe build() {
		validateInit();
		return recipe;
	}

	/**
	 * Registers the recipe
	 */
	public void register() {
		Recipe recipe = build();
		Bukkit.addRecipe(recipe);
	}

}
