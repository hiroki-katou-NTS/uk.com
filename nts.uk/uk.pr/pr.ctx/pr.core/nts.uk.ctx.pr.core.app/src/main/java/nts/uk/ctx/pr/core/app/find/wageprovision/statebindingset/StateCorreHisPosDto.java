package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisPo;

import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（職位）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorreHisPosDto {

    
    /**
    * 履歴ID
    */
    private String hisId;
    
    /**
    * 開始年月
    */
    private Integer startYearMonth;
    
    /**
    * 終了年月
    */
    private Integer endYearMonth;
    
    
    public static List<StateCorreHisPosDto> fromDomain(StateCorreHisPo domain) {
        return domain.getHistory().stream().map(item -> {
            return new StateCorreHisPosDto(item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
    }
    
}
