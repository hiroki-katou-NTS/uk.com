package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;

@Value
public class StateUseUnitSettingCommand{
    
    /**
    * 会社ID
    */
    private String companyID;
    
    /**
    * マスタ利用区分
    */
    private int masterUse;
    
    /**
    * 個人利用区分
    */
    private int individualUse;
    
    /**
    * 利用マスタ
    */
    private Integer usageMaster;
    

}
