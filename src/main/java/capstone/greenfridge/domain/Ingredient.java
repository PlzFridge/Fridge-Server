package capstone.greenfridge.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class Ingredient {
    private final Long ingredientId;
    private final String foodName;
    private final String foodImg;
    private final Long carbonOutput;
    private final String disposal;
    private final LocalDate durationAt;
    private final LocalDate storedAt;
}
