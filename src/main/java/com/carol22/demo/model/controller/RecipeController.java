package com.carol22.demo.model.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carol22.demo.dto.RecipeDTO;
import com.carol22.demo.model.Recipe;
import com.carol22.demo.servise.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Gesstion de Recetas", description = "operaciones relacionadas con la creacion, consulta y gestion de recetas")
public class RecipeController {
    
    private final RecipeService recipeService;
    
    @PostMapping
    @Operation(
        summary = "Crear una nueva recta", 
        description = "Crea una receta en el sistema a partir de los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Receta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "datos invalidos para la receta")
    })

    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.createRecipe(recipeDTO);
        return new ResponseEntity<>(recipe, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar receta",
        description = "Actulizar la informacion de una receta existente mediante su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
    })

    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.ok(recipe);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener receta por ID",
        description = "Retorna una receta especifica segun su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta encontrada"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
    })

    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.getRecipe(id);
        return ResponseEntity.ok(recipe);
    }
    
    @GetMapping
    @Operation(
        summary = "Listar todas las recetas",
        description = "Obtiene todas las recetas activas registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de recetas obtenida correctamente") 
    })

    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllActiveRecipes());
    }
    
    @GetMapping("/search")
    @Operation(
        summary = "Buscar recetas",
        description = "Buscar recetas que coincidan con una palabra clave"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultados de la busqueda obtenidos correctamente") 
    })

    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String keyword) {
        return ResponseEntity.ok(recipeService.searchRecipes(keyword));
    }
    
    @PatchMapping("/{id}/deactivate")
    @Operation(
        summary = "Desactivar receta",
        description = "Marca una receta como inactiva"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta desactivada correctamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
    })

    public ResponseEntity<Void> deactivateRecipe(@PathVariable Long id) {
        recipeService.deactivateRecipe(id);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{id}/activate")
    @Operation(
        summary = "Activar receta",
        description = "Activa una receta previamente desactivada"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta activada correctamente"),
        @ApiResponse(responseCode = "404", description = "Receta no encontrada"),
    })
    public ResponseEntity<Void> activateRecipe(@PathVariable Long id) {
        recipeService.activateRecipe(id);
        return ResponseEntity.ok().build();
    }
}
