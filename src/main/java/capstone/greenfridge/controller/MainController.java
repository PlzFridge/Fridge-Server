package capstone.greenfridge.controller;

import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.domain.response.FridgeCRUD;
import capstone.greenfridge.domain.response.FridgeInfo;
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
    @ResponseStatus(HttpStatus.CREATED)
    public FridgeCRUD saveIngredient(@RequestBody IngredientDTO ingredientDTO){
        return ingredientService.saveIngredient(ingredientDTO);
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

}
