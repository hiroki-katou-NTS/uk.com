package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisIndivi;

import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（個人）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorreHisIndiviDto {

    private String hisId;

    private Integer startYearMonth;

    private Integer endYearMonth;

    
    public static List<StateCorreHisIndiviDto> fromDomain(StateCorreHisIndivi domain) {
        return domain.getHistory().stream().map(item -> new StateCorreHisIndiviDto(
                item.identifier(), item.start().v(), item.end().v())).collect(Collectors.toList());
    }
    
}
