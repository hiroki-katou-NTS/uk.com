package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ExtractionCondtionsDto {

    private List<FixedExtractionConditionDto> conditions;

    private List<OptionalItemDto> optionalItems;

}
