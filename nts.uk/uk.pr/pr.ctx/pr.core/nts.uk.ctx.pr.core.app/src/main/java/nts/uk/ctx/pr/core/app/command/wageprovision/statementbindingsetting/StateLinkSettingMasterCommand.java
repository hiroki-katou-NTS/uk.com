package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;

@Value
public class StateLinkSettingMasterCommand {
    
    /**
    * 履歴ID
    */
    private String hisId;
    
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
    

}
