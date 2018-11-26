package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisDepar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（部門）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorreHisDeparDto {
    
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
    
    
    public static List<StateCorreHisDeparDto> fromDomain(String cid, StateCorreHisDepar domain) {
        List<StateCorreHisDeparDto> stateCorreHisDeparDto = new ArrayList<>();

        stateCorreHisDeparDto = domain.getHistory().stream().map(item -> new StateCorreHisDeparDto(cid,item.identifier(),item.start().v(),item.end().v())).collect(Collectors.toList());

        return stateCorreHisDeparDto;
    }
    
}
