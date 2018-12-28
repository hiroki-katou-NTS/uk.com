package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateCorreHisCom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 明細書紐付け履歴（会社）: DTO
 */
@AllArgsConstructor
@Value
public class StateCorreHisComDto {

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


    public static List<StateCorreHisComDto> fromDomain(String cid, StateCorreHisCom domain) {

        List<StateCorreHisComDto> listStateCorreHisComDto = new ArrayList<StateCorreHisComDto>();
        if(domain.getHistory().size() > 0) {
            listStateCorreHisComDto = domain.getHistory().stream().map(item -> new StateCorreHisComDto(cid, item.identifier(), item.start().v(), item.end().v())).collect(Collectors.toList());
        }
        return listStateCorreHisComDto;
    }
}
