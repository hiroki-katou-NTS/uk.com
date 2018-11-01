package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;

/**
* 明細書紐付け設定（マスタ基準日）: DTO
*/
@AllArgsConstructor
@Value
public class StateLinkSettingDateDto
{
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * マスタ基準日
    */
    private GeneralDate date;
    
    
    public static StateLinkSettingDateDto fromDomain(StateLinkSettingDate domain) {
        return new StateLinkSettingDateDto(domain.getHistoryID(), domain.getDate());
    }
    
}
