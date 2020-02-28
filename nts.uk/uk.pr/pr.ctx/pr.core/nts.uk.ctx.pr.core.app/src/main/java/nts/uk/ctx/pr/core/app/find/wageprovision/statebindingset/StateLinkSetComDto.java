package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetCom;

/**
* 明細書紐付け設定（会社）: DTO
*/
@AllArgsConstructor
@Value
public class StateLinkSetComDto {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * 給与明細書
    */
    private String salaryCode;

    
    /**
    * 賞与明細書
    */
    private String bonusCode;

    
    
    public static StateLinkSetComDto fromDomain(StateLinkSetCom domain)
    {
        return new StateLinkSetComDto(domain.getHistoryID(),
                domain.getSalaryCode().isPresent() ? domain.getSalaryCode().get().v() : null,
                domain.getBonusCode().isPresent() ? domain.getBonusCode().get().v() : null);
    }
    
}
