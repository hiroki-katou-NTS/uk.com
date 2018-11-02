package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateCorrelationHisEmployee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（雇用）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorrelationHisEmployeeDto
{
    
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
    
    
    public static List<StateCorrelationHisEmployeeDto> fromDomain(String cid, StateCorrelationHisEmployee domain)
    {
        List<StateCorrelationHisEmployeeDto> stateCorrelationHisEmployeeDto = new ArrayList<>();
        stateCorrelationHisEmployeeDto = domain.getHistory().stream().map(item ->{
            return new StateCorrelationHisEmployeeDto(cid, item.identifier(), item.start().v(),item.end().v());
        }).collect(Collectors.toList());
        return stateCorrelationHisEmployeeDto;
    }
    
}
