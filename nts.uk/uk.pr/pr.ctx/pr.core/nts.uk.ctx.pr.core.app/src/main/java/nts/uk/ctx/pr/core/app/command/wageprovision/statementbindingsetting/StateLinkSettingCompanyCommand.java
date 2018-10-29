package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;

@Value
public class StateLinkSettingCompanyCommand
{
    
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
    

}
