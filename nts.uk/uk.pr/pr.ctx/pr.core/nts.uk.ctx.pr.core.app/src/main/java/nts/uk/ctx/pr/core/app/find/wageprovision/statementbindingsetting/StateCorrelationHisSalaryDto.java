package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisSalary;

import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（給与分類）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorrelationHisSalaryDto {


    private String hisId;

    private Integer startYearMonth;

    private Integer endYearMonth;
    
    
    public static List<StateCorrelationHisSalaryDto> fromDomain(StateCorrelationHisSalary domain) {
        return domain.getHistory().stream().map(item -> {
            return new StateCorrelationHisSalaryDto(item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
    }
    
}
