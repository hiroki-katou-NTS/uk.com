package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisDeparment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（部門）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorrelationHisDeparmentDto {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 開始年月
    */
    private int startYearMonth;
    
    /**
    * 終了年月
    */
    private int endYearMonth;
    
    
    public static List<StateCorrelationHisDeparmentDto> fromDomain(String cid, StateCorrelationHisDeparment domain) {
        List<StateCorrelationHisDeparmentDto> stateCorrelationHisDeparmentDto = new ArrayList<>();

        stateCorrelationHisDeparmentDto = domain.getHistory().stream().map(item ->{
            return new StateCorrelationHisDeparmentDto(cid,item.identifier(),item.start().v(),item.end().v());
        }).collect(Collectors.toList());

        return stateCorrelationHisDeparmentDto;
    }
    
}
