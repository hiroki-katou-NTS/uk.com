package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;

import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisEm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（雇用）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorreHisEmpDto {
    
    /**
    * 会社ID
    */
    private String cid;
    
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
    
    
    public static List<StateCorreHisEmpDto> fromDomain(String cid, StateCorreHisEm domain)
    {
        List<StateCorreHisEmpDto> stateCorreHisEmpDto = new ArrayList<>();
        stateCorreHisEmpDto = domain.getHistory().stream().map(item -> new StateCorreHisEmpDto(cid, item.identifier(), item.start().v(),item.end().v())).collect(Collectors.toList());
        return stateCorreHisEmpDto;
    }
    
}
