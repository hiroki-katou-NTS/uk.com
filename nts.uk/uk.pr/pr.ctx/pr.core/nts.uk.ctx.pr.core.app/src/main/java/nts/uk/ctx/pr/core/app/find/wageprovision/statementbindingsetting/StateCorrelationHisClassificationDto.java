package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import java.util.stream.Collectors;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisClassification;

/**
* 明細書紐付け履歴（分類）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorrelationHisClassificationDto {

    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 開始年月
    */
    private int startYearMonth;
    
    /**
    * 終了年月
    */
    private int endYearMonth;
    
    
    public static List<StateCorrelationHisClassificationDto> fromDomain(StateCorrelationHisClassification domain) {
        return domain.getHistory().stream().map(item -> {
            return new StateCorrelationHisClassificationDto(item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
    }
    
}
