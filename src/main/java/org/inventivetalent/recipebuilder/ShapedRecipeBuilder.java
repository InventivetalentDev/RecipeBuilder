package org.inventivetalent.recipebuilder;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.inventivetalent.itembuilder.ItemBuilder;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public ShapedRecipeBuilder(ItemStack result) {
		super(result);
	}

	@Override
	protected void initRecipe(ItemStack result) {
		if (recipe == null) { recipe = new ShapedRecipe(result); }
	}

	@Override
	protected void initRecipe(NamespacedKey key, ItemStack result) {
		if (recipe == null) { recipe = new ShapedRecipe(key, result); }
	}

	private ShapedRecipe getRecipe() {
		return (ShapedRecipe) this.recipe;
	}

	/**
	 * Loads the recipe from a {@link ConfigurationSection} See <link>https://paste.inventivetalent.org/irorulawev.yml</link> for an example configuration
	 *
	 * @param section {@link ConfigurationSection}
	 * @return the ShapedRecipeBuilder
	 */
	@Override
	public ShapedRecipeBuilder fromConfig(ConfigurationSection section) {
		super.fromConfig(section);
		if (section.contains("shape")) {
			List<String> shapeList = section.getStringList("shape");
			withShape(shapeList.toArray(new String[shapeList.size()]));
		}
		if (section.contains("ingredients")) {
			ConfigurationSection ingredientSection = section.getConfigurationSection("ingredients");
			Set<String> keys = ingredientSection.getKeys(false);

			for (String key : keys) {
				char keyChar = key.charAt(0);

				ItemBuilder itemBuilder = new ItemBuilder();
				itemBuilder.fromConfig(section.getConfigurationSection("ingredients." + key));
				ItemStack item = itemBuilder.build();

				withIngredient(keyChar, item);
			}
		}
		return this;
	}

	@Override
	public ShapedRecipeBuilder forResult(ItemStack result) {
		return (ShapedRecipeBuilder) super.forResult(result);
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
	 * @param ingredient ingredient {@link MaterialData}
	 * @return the ShapedRecipeBuilder
	 */
	public ShapedRecipeBuilder withIngredient(char key, MaterialData ingredient) {
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
