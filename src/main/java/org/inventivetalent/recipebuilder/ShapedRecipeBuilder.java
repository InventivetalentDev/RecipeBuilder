package org.inventivetalent.recipebuilder;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.lang.reflect.Field;
import java.util.Map;

public class ShapedRecipeBuilder extends RecipeBuilder {

	/**
	 * Constructs a new ShapedRecipeBuilder without content
	 */
	public ShapedRecipeBuilder() {
	}

	/**
	 * Constructs a new ShapedRecipeBuilder for the specified result
	 *
	 * @param result result {@link ItemStack}
	 */
	public ShapedRecipeBuilder(NamespacedKey key, ItemStack result) {
		super(key, result);
	}

	@Override
	protected void initRecipe(NamespacedKey key, ItemStack result) {
		if (recipe == null) { recipe = new ShapedRecipe(key, result); }
	}

	private ShapedRecipe getRecipe() {
		return (ShapedRecipe) this.recipe;
	}

	/**
	 * Sets the shape of the recipe
	 *
	 * @param shape Shape of the recipe
	 * @return the ShapedRecipeBuilder
	 */
	public ShapedRecipeBuilder withShape(String... shape) {
		validateInit();
		getRecipe().shape(shape);
		return this;
	}

	/**
	 * Assigns a shape key to an ingredient
	 *
	 * @param key        key given in the shape
	 * @param ingredient ingredient {@link Material}
	 * @return the ShapedRecipeBuilder
	 */
	public ShapedRecipeBuilder withIngredient(char key, Material ingredient) {
		validateInit();
		getRecipe().setIngredient(key, ingredient);
		return this;
	}

	/**
	 * Assigns a shape key to an ingredient
	 *
	 * @param key        key given in the shape
	 * @param ingredient ingredient {@link ItemStack}
	 * @return the ShapedRecipeBuilder
	 */
	public ShapedRecipeBuilder withIngredient(char key, ItemStack ingredient) {
		validateInit();

		try {
			((Map<Character, ItemStack>) INGREDIENT_MAP.get(getRecipe())).put(Character.valueOf(key), ingredient);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public ShapedRecipe build() {
		return (ShapedRecipe) super.build();
	}

	static Field INGREDIENT_MAP;

	static {
		try {
			INGREDIENT_MAP = ShapedRecipe.class.getDeclaredField("ingredients");
			INGREDIENT_MAP.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
