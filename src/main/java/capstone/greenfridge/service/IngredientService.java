package capstone.greenfridge.service;

import capstone.greenfridge.domain.FridgeListVO;
import capstone.greenfridge.domain.Ingredient.Ingredient;
import capstone.greenfridge.domain.Ingredient.IngredientDTO;
import capstone.greenfridge.domain.Ingredient.IngredientVO;
import capstone.greenfridge.domain.MyFridgeVO;
import capstone.greenfridge.domain.Recipe;
import capstone.greenfridge.domain.response.FridgeCRUD;
import capstone.greenfridge.domain.response.FridgeInfo;
import capstone.greenfridge.domain.response.RecipeData;
import capstone.greenfridge.domain.response.RecipeInfo;
import capstone.greenfridge.repository.FridgeMapper;
import capstone.greenfridge.repository.IngredientMapper;
import capstone.greenfridge.repository.RecipeMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static capstone.greenfridge.domain.ExceptionMessageConst.*;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientMapper ingredientMapper;
    private final FridgeMapper fridgeMapper;
    private final RecipeMapper recipeMapper;
    private final static Map<String,Long> ingredientMap=new LinkedHashMap<>();
    private final static Map<Long,String> ingredientMapInverse=new LinkedHashMap<>();
    private final static Map<Long,Long> ingredientMapCarbon=new LinkedHashMap<>();
    private final static Map<String,Set<Long>> recipeIngredient=new LinkedHashMap<>();
    private final static Map<String,String> ingredientNameMapping=new HashMap<>();

    @PostConstruct
    public void init(){
        List<IngredientVO> initIngredientData=ingredientMapper.toInitIngredientMap();
        List<Recipe> initRecipeData=recipeMapper.getRecipeList();

        for(IngredientVO i:initIngredientData){//재료종류 불러와서 이름,id로 저장
            ingredientMap.put(i.getIngredientName(),i.getIngredientId());
            ingredientMapInverse.put(i.getIngredientId(),i.getIngredientName());
            ingredientMapCarbon.put(i.getIngredientId(),i.getCarbonOutput());
        }
        for(Recipe i:initRecipeData){
            String[] temp=i.getIngredientList().split("/");
            Set<Long> parsingList=new HashSet<>();

            for(String j:temp){
                parsingList.add(ingredientMap.get(j));
            }
            recipeIngredient.put(i.getRecipeName(),parsingList);
        }

        //레시피는 순서대로, 재료 ID가 각각 Set 으로 저장되어있는 상태
        nameMapping();
    }

    public void nameMapping(){
        ingredientNameMapping.put("potato","감자");
        ingredientNameMapping.put("tomato","토마토");
        ingredientNameMapping.put("eggplant","가지");
        ingredientNameMapping.put("cucumber","오이");
        ingredientNameMapping.put("pear","배");
        ingredientNameMapping.put("apple","사자");
        ingredientNameMapping.put("carrot","당근");
        ingredientNameMapping.put("onion","양파");
        ingredientNameMapping.put("garlic","마늘");
        ingredientNameMapping.put("spring_onion","대파");
        ingredientNameMapping.put("sweet_potato","고구마");
        ingredientNameMapping.put("spinach","시금치");
        ingredientNameMapping.put("bean_sprout","콩나물");
        ingredientNameMapping.put("bell_pepper","피망");
        ingredientNameMapping.put("zucchini","애호박");
        ingredientNameMapping.put("mushroom","버섯");
        ingredientNameMapping.put("fish_cake","어묵");
        ingredientNameMapping.put("egg","계란");
        ingredientNameMapping.put("kimchi","김치");
        ingredientNameMapping.put("chicken","닭고기");
        ingredientNameMapping.put("beef","소고기");
        ingredientNameMapping.put("pork","돼지고기");
        ingredientNameMapping.put("sausage","소시지");
        ingredientNameMapping.put("tofu","두부");
        ingredientNameMapping.put("shrimp","새우");
        ingredientNameMapping.put("rice_cake","떡");
        ingredientNameMapping.put("cheese","치즈");
        ingredientNameMapping.put("grape","포도");
        ingredientNameMapping.put("fish","고등어");
        ingredientNameMapping.put("pepper","고추");
        ingredientNameMapping.put("raddish","무");

    }

    public FridgeCRUD saveIngredient(IngredientDTO ingredientDTO){

        String ingredientName= ingredientDTO.getIngredientName();
        LocalDate durationAt;

        Optional<Ingredient> tempIngredient=ingredientMapper.findByIngredientName(ingredientName);


        Boolean success= tempIngredient.isPresent();
        String message=(tempIngredient.isEmpty())? FAILED_SAVE_INGREDIENT.getMessage()
                : SUCCESS_SAVE_INGREDIENT.getMessage();
        HttpStatus status=(tempIngredient.isEmpty())? HttpStatus.NO_CONTENT
                : HttpStatus.CREATED;


        if(success) {//들어온 입력에 해당하는 재료가 존재할시
            if (ingredientDTO.getDurationAt() == null) {
                LocalDate todayLocalDate = LocalDate.now();
                Long duration = tempIngredient.get().getDurationAt();

                durationAt = todayLocalDate.plusDays(duration);
            } else {
                durationAt = ingredientDTO.getDurationAt();
            }

            MyFridgeVO fridgeVO = MyFridgeVO.builder()
                    .ingredientId(tempIngredient.get().getIngredientId())
                    .ingredientName(ingredientDTO.getIngredientName())
                    .durationAt(durationAt)
                    .storedAt(LocalDate.now())
                    .build();
            fridgeMapper.saveIngredient(fridgeVO);
        }

        return FridgeCRUD.builder()
                .status(status.value())
                .success(success)
                .message(message)
                .build();
    }

    public FridgeCRUD saveIngredientEng(IngredientDTO ingredientDTO) {

        String tempIngredientName=ingredientNameMapping.get(ingredientDTO.getIngredientName());
        LocalDate durationAt;

        Optional<Ingredient> tempIngredient=ingredientMapper.findByIngredientName(tempIngredientName);

        boolean success= tempIngredient.isPresent();
        String message=(tempIngredient.isEmpty())? FAILED_SAVE_INGREDIENT.getMessage()
                : SUCCESS_SAVE_INGREDIENT.getMessage();
        HttpStatus status=(tempIngredient.isEmpty())? HttpStatus.NO_CONTENT
                : HttpStatus.CREATED;

        if (success) {
            if (ingredientDTO.getDurationAt() == null) {
                LocalDate todayLocalDate = LocalDate.now();
                Long duration = tempIngredient.get().getDurationAt();

                durationAt = todayLocalDate.plusDays(duration);
            } else {
                durationAt = ingredientDTO.getDurationAt();
            }

            MyFridgeVO fridgeVO = MyFridgeVO.builder()
                    .ingredientId(tempIngredient.get().getIngredientId())
                    .ingredientName(tempIngredientName)
                    .durationAt(durationAt)
                    .storedAt(LocalDate.now())
                    .build();
            fridgeMapper.saveIngredient(fridgeVO);
        }
        return FridgeCRUD.builder()
                .status(status.value())
                .success(success)
                .message(message)
                .build();

    }

    public FridgeCRUD deleteIngredient(Long fridgeId){

        fridgeMapper.deleteIngredient(fridgeId);

        return FridgeCRUD.builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message(SUCCESS_DELETE_INGREDIENT.getMessage())
                .build();
    }

    public FridgeCRUD deleteAfterEat(String usedIngredient){
        List<String> ateIngredient=Arrays.asList(usedIngredient.split(","));
        fridgeMapper.deleteAfterEat(ateIngredient);

        return FridgeCRUD.builder()
                .status(HttpStatus.OK.value())
                .success(true)
                .message(SUCCESS_DELETE_INGREDIENT.getMessage())
                .build();
    }

    public FridgeInfo getFridgeList(){

        List<FridgeListVO> data=fridgeMapper.getFridgeList();

        return FridgeInfo.builder()
                .status(HttpStatus.OK.value())
                .message(SUCCESS_LOAD_LIST.getMessage())
                .data(data)
                .build();
    }

    public RecipeInfo recommendRecipe(){
        List<FridgeListVO> curFridgeData=fridgeMapper.getFridgeList();
        List<RecipeData> data=new ArrayList<>();
        long[] score=new long[recipeIngredient.size()+1];
        long[] maxIdxArray=new long[3];

        for(FridgeListVO i:curFridgeData){
            LocalDate temp= i.getDurationAt();
            LocalDate cur=LocalDate.now();
            long chronoUnit=ChronoUnit.DAYS.between(cur,temp);
            if(chronoUnit==0L){
                chronoUnit=1;
            }

            int idx=1;
            for(Set<Long> j:recipeIngredient.values()){
                if(j.contains(i.getIngredientId())){
                    score[idx]=score[idx]+1000/chronoUnit;
                }
                idx++;
            }
        }

        //각 레시피별 점수 매기기 완료된 상태
        for(int i=0;i<3;i++){
            long maxValue=0;
            int maxIdx=0;
            for(int j=0;j<=recipeIngredient.size();j++){
                if(maxValue<score[j]){
                    maxValue=score[j];
                    maxIdx=j;
                }
            }
            maxIdxArray[i]=maxIdx;
            score[maxIdx]=-1;
        }

        //점수 제일 높은 레시피의 id 3개 maxIdxArray에 저장된 상태
        for(int i=0;i<3&&maxIdxArray[i]!=0;i++){

            Recipe selected=recipeMapper.getRecipe(maxIdxArray[i]);
            List<String> existIngredient=new ArrayList<>();
            List<String> nonExistIngredient=new ArrayList<>();
            Long carbonOutput=0L;

            
            Set<Long> ingredientList=recipeIngredient.get(selected.getRecipeName());
            for(Long j:ingredientList){
                boolean flag=false;

                for(FridgeListVO k:curFridgeData){//현재 가지고있는 재료 리스트 탐색
                    if(j.equals(k.getIngredientId())) {
                        flag=true;
                        break;
                    }
                }
                if(flag){//만드는데 필요한 재료가 가지고 있는 재료중에 있을경우
                    carbonOutput+=ingredientMapCarbon.get(j);
                    existIngredient.add(ingredientMapInverse.get(j));
                }
                else{
                    nonExistIngredient.add(ingredientMapInverse.get(j));
                }
            }

            RecipeData temp=RecipeData.builder()
                    .recipeId(selected.getRecipeId())
                    .recipeImg(selected.getRecipeImg())
                    .recipeName(selected.getRecipeName())
                    .ingredientList(selected.getIngredientList())
                    .method(selected.getMethod())
                    .existList(existIngredient)
                    .notExistList(nonExistIngredient)
                    .carbon(carbonOutput)
                    .build();
            data.add(temp);


        }

        return RecipeInfo.builder()
                .status(HttpStatus.OK.value())
                .message(SUCCESS_RECOMMEND_RECIPE.getMessage())
                .data(data)
                .build();
    }

}