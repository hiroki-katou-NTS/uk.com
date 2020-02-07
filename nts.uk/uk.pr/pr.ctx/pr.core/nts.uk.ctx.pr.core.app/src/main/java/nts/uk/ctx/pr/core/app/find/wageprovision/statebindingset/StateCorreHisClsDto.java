package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisCls;

import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（分類）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorreHisClsDto {

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
    
    
    public static List<StateCorreHisClsDto> fromDomain(StateCorreHisCls domain) {
        return domain.getHistory().stream().map(item -> {
            return new StateCorreHisClsDto(item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
    }
    
}
