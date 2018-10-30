package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingMaster;

/**
* 明細書紐付け設定（マスタ）: DTO
*/
@AllArgsConstructor
@Value
public class StateLinkSettingMasterDto
{
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * マスタコード
    */
    private String masterCode;
    
    /**
    * 給与明細書
    */
    private String salaryCode;
    
    /**
    * 賞与明細書
    */
    private String bonusCode;
    
    
    public static StateLinkSettingMasterDto fromDomain(StateLinkSettingMaster domain) {
        return new StateLinkSettingMasterDto(domain.getHistoryID(), domain.getMasterCode().v(), domain.getSalaryCode().get().v(), domain.getBonusCode().get().v());
    }
    
}
