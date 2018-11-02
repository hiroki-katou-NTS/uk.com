package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisPosition;

import java.util.List;
import java.util.stream.Collectors;
/**
* 明細書紐付け履歴（職位）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorrelationHisPositionDto {

    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 開始年月
    */
    private Integer startYearMonth;
    
    /**
    * 終了年月
    */
    private Integer endYearMonth;
    
    
    public static List<StateCorrelationHisPositionDto> fromDomain(StateCorrelationHisPosition domain) {
        return domain.getHistory().stream().map(item -> {
            return new StateCorrelationHisPositionDto(item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
    }
    
}
