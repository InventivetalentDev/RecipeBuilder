package org.inventivetalent.recipebuilder;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public abstract class RecipeBuilder {

	protected Recipe recipe;

	protected RecipeBuilder() {
	}

	protected RecipeBuilder(NamespacedKey key, ItemStack result) {
		forResult(key, result);
	}

	protected abstract void initRecipe(NamespacedKey key, ItemStack result);

	protected void validateInit() {
		if (recipe == null) { throw new IllegalStateException("Recipe not yet initiated"); }
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
