package capstone.greenfridge.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageConst {
    SUCCESS_SAVE_INGREDIENT("재료등록이 완료되었습니다."),
    FAILED_SAVE_INGREDIENT("재료등록이 실패하였습니다."),
    SUCCESS_DELETE_INGREDIENT("등록된 재료가 제거되었습니다."),
    SUCCESS_LOAD_LIST("재료현황 리스트를 가져왔습니다."),
    SUCCESS_RECOMMEND_RECIPE("레시피 추천을 완료했습니다.");

    private final String message;
}
