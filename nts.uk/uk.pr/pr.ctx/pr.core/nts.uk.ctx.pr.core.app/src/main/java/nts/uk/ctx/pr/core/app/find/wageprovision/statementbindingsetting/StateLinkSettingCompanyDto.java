package nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingCompany;

/**
* 明細書紐付け設定（会社）: DTO
*/
@AllArgsConstructor
@Value
public class StateLinkSettingCompanyDto {
    
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

    
    
    public static StateLinkSettingCompanyDto fromDomain(StateLinkSettingCompany domain)
    {
        return new StateLinkSettingCompanyDto(domain.getHistoryID(), domain.getSalaryCode().get().v(), domain.getBonusCode().get().v());
    }
    
}
