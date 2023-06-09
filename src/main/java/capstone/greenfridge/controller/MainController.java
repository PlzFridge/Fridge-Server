package capstone.greenfridge.controller;

import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.domain.response.FridgeCRUD;
import capstone.greenfridge.domain.response.FridgeInfo;
import capstone.greenfridge.domain.response.RecipeInfo;
import capstone.greenfridge.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static capstone.greenfridge.domain.ExceptionMessageConst.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

    private final IngredientService ingredientService;
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public FridgeCRUD saveIngredient(@RequestBody IngredientDTO ingredientDTO){
        return ingredientService.saveIngredient(ingredientDTO);
    }
    @PostMapping("/save-english")
    @ResponseStatus(HttpStatus.CREATED)
    public FridgeCRUD saveIngredientEng(@RequestBody IngredientDTO ingredientDTO){
        return ingredientService.saveIngredientEng(ingredientDTO);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public FridgeCRUD deleteIngredient(@RequestParam Long fridgeId){
        return ingredientService.deleteIngredient(fridgeId);
    }
    @GetMapping("/fridge-list")
    @ResponseStatus(HttpStatus.OK)
    public FridgeInfo curIngredientList(){
        return ingredientService.getFridgeList();
    }

    @GetMapping("/recommend")
    @ResponseStatus(HttpStatus.OK)
    public RecipeInfo recommendRecipe(){
        return ingredientService.recommendRecipe();
    }

    @PostMapping("/delete-after")
    @ResponseStatus(HttpStatus.OK)
    public FridgeCRUD deleteAfterEat(@RequestBody Map<String,String> deleteList){
        return ingredientService.deleteAfterEat(deleteList.get("deleteList"));
    }
}
