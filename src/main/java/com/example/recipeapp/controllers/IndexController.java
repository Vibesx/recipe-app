package com.example.recipeapp.controllers;

import com.example.recipeapp.model.Category;
import com.example.recipeapp.model.Recipe;
import com.example.recipeapp.model.UnitOfMeasure;
import com.example.recipeapp.repositories.CategoryRepository;
import com.example.recipeapp.repositories.RecipeRepository;
import com.example.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;
    private RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model model) {

        Recipe guacamoleRecipe = recipeRepository.findFirstByTitleContains("guacamole").get();

        model.addAttribute("guacamoleTitle", guacamoleRecipe.getTitle());
        model.addAttribute("guacamoleCategories", guacamoleRecipe.getCategories());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getCookTime());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getPrepTime());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getServings());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getDescription());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getDifficulty());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getDirections());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getIngredients());
        model.addAttribute("guacamoleTitle", guacamoleRecipe.getNotes());

        /*Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Pinch");
        categoryOptional.ifPresent(a -> System.out.println("Cat Id is: " + a.getId()));
        unitOfMeasureOptional.ifPresent(a -> System.out.println("UOM ID is: " + a.getId()));
        //model.addAttribute("test", "Test String!!!123sssss");*/
        return "index";
    }
}
