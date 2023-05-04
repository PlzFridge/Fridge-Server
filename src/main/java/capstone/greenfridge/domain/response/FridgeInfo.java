package capstone.greenfridge.domain.response;

import capstone.greenfridge.domain.FridgeListVO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FridgeInfo {
    private final Integer status;
    private final String message;
    private final List<FridgeListVO> data;
}
