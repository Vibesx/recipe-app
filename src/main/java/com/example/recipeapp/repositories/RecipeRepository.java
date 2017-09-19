package com.example.recipeapp.repositories;

import com.example.recipeapp.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Optional<Recipe> findFirstByTitleContains(String title);
}
