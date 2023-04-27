package capstone.greenfridge.controller;

import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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


}
