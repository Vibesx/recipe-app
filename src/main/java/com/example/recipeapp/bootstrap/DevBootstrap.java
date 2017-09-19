package com.example.recipeapp.bootstrap;

import com.example.recipeapp.model.*;
import com.example.recipeapp.model.enums.Difficulty;
import com.example.recipeapp.model.enums.UnitOfMeasureEnum;
import com.example.recipeapp.repositories.CategoryRepository;
import com.example.recipeapp.repositories.RecipeRepository;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
@PropertySource("classpath:application.yml")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    private Set<UnitOfMeasure> unitsOfMeasure;

    @Value("${recipe.guacamole.description}")
    String guacamoleRecipeDescription;
    @Value("${recipe.guacamole.directions}")
    String guacamoleRecipeDirections;
    @Value("${recipe.guacamole.source}")
    String guacamoleRecipeSource;
    @Value("${recipe.guacamole.url}")
    String guacamoleRecipeUrl;
    @Value("${recipe.guacamole.notes}")
    String guacamoleRecipeNotes;
    @Value("${recipe.guacamole.title}")
    String guacamoleRecipeTitle;


    public DevBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    private void initData() {
        unitsOfMeasure = getAllUnitsOfMeasure();

        Recipe guacamoleRecipe = new Recipe();
        Set<Category> guacamoleCategories = new HashSet<>();
        Set<Ingredient> guacamoleIngredients = new HashSet<>();
        Notes recipeNotes = new Notes(guacamoleRecipe, guacamoleRecipeNotes);
        categoryRepository.findByDescription("Mexican").ifPresent(guacamoleCategories::add);
        categoryRepository.findByDescription("Sauce").ifPresent(guacamoleCategories::add);
        guacamoleIngredients.add(new Ingredient("ripe avocados", "2", guacamoleRecipe));
        guacamoleIngredients.add(new Ingredient("Kosher salt", "1/2", getUnitOfMeasure(UnitOfMeasureEnum.TEASPOON), guacamoleRecipe));
        guacamoleIngredients.add(new Ingredient("fresh lime juice or lemon juice", "1", getUnitOfMeasure(UnitOfMeasureEnum.TABLESPOON), guacamoleRecipe));
        guacamoleIngredients.add(new Ingredient("minced red onion or thinly sliced green onion", "2", getUnitOfMeasure(UnitOfMeasureEnum.TABLESPOON), guacamoleRecipe));
        guacamoleIngredients.add(new Ingredient("serrano chiles, stems and seeds removed, minced", "1-2", guacamoleRecipe));
        guacamoleIngredients.add(new Ingredient("cilantro (leaves and tender stems), finely chopped", "2", getUnitOfMeasure(UnitOfMeasureEnum.TABLESPOON), guacamoleRecipe));
        guacamoleIngredients.add(new Ingredient("freshly grated black pepper", "1", getUnitOfMeasure(UnitOfMeasureEnum.DASH), guacamoleRecipe));
        guacamoleIngredients.add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", "1/2", guacamoleRecipe));

        guacamoleRecipe.setTitle(guacamoleRecipeTitle);
        guacamoleRecipe.setIngredients(guacamoleIngredients);
        guacamoleRecipe.setCategories(guacamoleCategories);
        guacamoleRecipe.setCookTime(0);
        guacamoleRecipe.setDifficulty(Difficulty.MODERATE);
        guacamoleRecipe.setPrepTime(10);
        guacamoleRecipe.setServings(3);
        guacamoleRecipe.setSource(guacamoleRecipeSource);
        guacamoleRecipe.setUrl(guacamoleRecipeUrl);
        guacamoleRecipe.setDescription(guacamoleRecipeDescription);
        guacamoleRecipe.setDirections(guacamoleRecipeDirections);
        guacamoleRecipe.setNotes(recipeNotes);

        recipeRepository.save(guacamoleRecipe);
    }

    private Set<UnitOfMeasure> getAllUnitsOfMeasure() {

        Iterator it = unitOfMeasureRepository.findAll().iterator();
        Set<UnitOfMeasure> result = new HashSet<>();

        while(it.hasNext()) {
            result.add((UnitOfMeasure) it.next());
        }

        return result;
    }

    private UnitOfMeasure getUnitOfMeasure(UnitOfMeasureEnum uom) {
        UnitOfMeasure result = null;
        System.out.println(uom.toString());
        if(unitsOfMeasure != null) {
            result = unitsOfMeasure.stream().filter(a -> a.getDescription().equalsIgnoreCase(uom.toString())).findFirst().get();
        }
        if(result != null) {
            return result;
        }
        return null;
    }
}
