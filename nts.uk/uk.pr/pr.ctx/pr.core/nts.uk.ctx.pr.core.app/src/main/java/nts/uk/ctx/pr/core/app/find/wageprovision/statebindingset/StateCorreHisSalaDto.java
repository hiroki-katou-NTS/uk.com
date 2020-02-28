package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisSala;

import java.util.List;
import java.util.stream.Collectors;

/**
* 明細書紐付け履歴（給与分類）: DTO
*/
@AllArgsConstructor
@Value
public class StateCorreHisSalaDto {


    private String hisId;

    private Integer startYearMonth;

    private Integer endYearMonth;
    
    
    public static List<StateCorreHisSalaDto> fromDomain(StateCorreHisSala domain) {
        return domain.getHistory().stream().map(item -> {
            return new StateCorreHisSalaDto(item.identifier(), item.start().v(), item.end().v());
        }).collect(Collectors.toList());
    }
    
}
