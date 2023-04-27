package capstone.greenfridge.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessageConst {
    SUCCESS_SAVE_INGREDIENT("재료등록이 완료되었습니다.");

    private final String message;
}
