package capstone.greenfridge.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FridgeCRUD {
    private final Integer status;
    private final Boolean success;
    private final String message;
}
