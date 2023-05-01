package capstone.greenfridge.controller;

import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static capstone.greenfridge.domain.ExceptionMessageConst.*;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final IngredientService ingredientService;
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public String saveIngredient(@RequestBody IngredientDTO ingredientDTO){
        ingredientService.saveIngredient(ingredientDTO);
        return SUCCESS_SAVE_INGREDIENT.getMessage();
    }
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String deleteIngredient(@RequestParam Long ingredientId){
        ingredientService.deleteIngredient(ingredientId);
        return SUCCESS_DELETE_INGREDIENT.getMessage();
    }

}
