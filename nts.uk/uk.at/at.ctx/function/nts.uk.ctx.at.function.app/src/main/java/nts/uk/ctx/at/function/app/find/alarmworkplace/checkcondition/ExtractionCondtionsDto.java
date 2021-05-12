package nts.uk.ctx.at.function.app.find.alarmworkplace.checkcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class ExtractionCondtionsDto {

    private List<FixedExtractionConditionDto> conditions;

    private List<ExtractionSchelConDto> opSchelCons;

    private List<ExtractionMonConDto> opMonCons;

}
