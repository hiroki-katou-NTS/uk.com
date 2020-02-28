package nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.StateLinkSetDate;

/**
* 明細書紐付け設定（マスタ基準日）: DTO
*/
@AllArgsConstructor
@Value
public class StateLinkSetDateDto {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * マスタ基準日
    */
    private GeneralDate date;
    
    
    public static StateLinkSetDateDto fromDomain(StateLinkSetDate domain) {
        return new StateLinkSetDateDto(domain.getHistoryID(), domain.getDate());
    }
    
}
