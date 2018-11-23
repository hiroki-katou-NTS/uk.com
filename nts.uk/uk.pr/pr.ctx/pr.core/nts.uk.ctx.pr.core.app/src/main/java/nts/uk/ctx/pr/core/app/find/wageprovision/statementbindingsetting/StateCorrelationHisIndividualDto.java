package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisIndividual;

import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（個人）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorrelationHisIndividualDto {

    private String hisId;

    private Integer startYearMonth;

    private Integer endYearMonth;

    
    public static List<StateCorrelationHisIndividualDto> fromDomain(StateCorrelationHisIndividual domain) {
        return domain.getHistory().stream().map(item -> new StateCorrelationHisIndividualDto(
                item.identifier(), item.start().v(), item.end().v())).collect(Collectors.toList());
    }
    
}
