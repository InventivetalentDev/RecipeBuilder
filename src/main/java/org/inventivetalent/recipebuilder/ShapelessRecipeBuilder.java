package org.inventivetalent.recipebuilder;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;
import org.inventivetalent.itembuilder.ItemBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class ShapelessRecipeBuilder extends RecipeBuilder {

	/**
	 * Constructs a new ShapelessRecipeBuilder without content
	 */
	public ShapelessRecipeBuilder() {
	}

	/**
	 * Constructs a new ShapelessRecipeBuilder for the specified result
	 *
	 * @param result result {@link ItemStack}
	 */
	public ShapelessRecipeBuilder(ItemStack result) {
		super(result);
	}

	@Override
	protected void initRecipe(ItemStack result) {
		if (recipe == null) { recipe = new ShapelessRecipe(result); }
	}

	@Override
	protected void initRecipe(NamespacedKey key, ItemStack result) {
		if (recipe == null) { recipe = new ShapedRecipe(key, result); }
	}

	private ShapelessRecipe getRecipe() {
		return (ShapelessRecipe) recipe;
	}

	/**
	 * Loads the recipe from a {@link ConfigurationSection}
	 * See <link>https://paste.inventivetalent.org/irorulawev.yml</link> for an example configuration
	 *
	 * @param section {@link ConfigurationSection}
	 * @return the ShapelessRecipeBuilder
	 */
	@Override
	public ShapelessRecipeBuilder fromConfig(ConfigurationSection section) {
		super.fromConfig(section);
		if (section.contains("ingredients")) {
			ConfigurationSection ingredientSection = section.getConfigurationSection("ingredients");
			Set<String> keys = ingredientSection.getKeys(false);

			for (String key : keys) {
				ItemBuilder itemBuilder = new ItemBuilder();
				itemBuilder.fromConfig(section.getConfigurationSection("ingredients." + key));
				ItemStack item = itemBuilder.build();

				withIngredient(item);
			}
		}

		return this;
	}

	@Override
	public ShapelessRecipeBuilder forResult(ItemStack result) {
		return (ShapelessRecipeBuilder) super.forResult(result);
	}

	/**
	 * Adds an ingredient to the recipe
	 *
	 * @param ingredient ingredient {@link Material}
	 * @return the ShapelessRecipeBuilder
	 */
	public ShapelessRecipeBuilder withIngredient(Material ingredient) {
		validateInit();
		getRecipe().addIngredient(ingredient);
		return this;
	}

	/**
	 * Adds an ingredient to the recipe
	 *
	 * @param ingredient ingredient {@link MaterialData}
	 * @return the ShapelessRecipeBuilder
	 */
	public ShapelessRecipeBuilder withIngredient(MaterialData ingredient) {
		validateInit();
		getRecipe().addIngredient(ingredient);
		return this;
	}

	/**
	 * Adds an ingredient to the recipe
	 *
	 * @param count      amount of the ingredient
	 * @param ingredient ingredient {@link Material}
	 * @return the ShapelessRecipeBuilder
	 */
	public ShapelessRecipeBuilder withIngredient(int count, Material ingredient) {
		validateInit();
		getRecipe().addIngredient(count, ingredient);
		return this;
	}

	/**
	 * Adds an ingredient to the recipe
	 *
	 * @param count      amount of the ingredient
	 * @param ingredient ingredient {@link MaterialData}
	 * @return the ShapelessRecipeBuilder
	 */
	public ShapelessRecipeBuilder withIngredient(int count, MaterialData ingredient) {
		validateInit();
		getRecipe().addIngredient(count, ingredient);
		return this;
	}

	/**
	 * Adds an ingredient to the recipe
	 *
	 * @param ingredient ingredient {@link ItemStack}
	 * @return the ShapelessRecipeBuilder
	 */
	public ShapelessRecipeBuilder withIngredient(ItemStack ingredient) {
		validateInit();

		try {
			((List<ItemStack>) RECIPE_LIST.get(getRecipe())).add(ingredient);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public ShapelessRecipe build() {
		return (ShapelessRecipe) super.build();
	}

	static Field RECIPE_LIST;

	static {
		try {
			RECIPE_LIST = ShapelessRecipe.class.getDeclaredField("ingredients");
			RECIPE_LIST.setAccessible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
