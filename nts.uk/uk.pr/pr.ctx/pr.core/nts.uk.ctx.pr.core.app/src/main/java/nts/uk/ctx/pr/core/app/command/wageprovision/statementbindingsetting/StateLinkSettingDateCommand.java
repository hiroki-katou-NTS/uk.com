package nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class StateLinkSettingDateCommand {
    
    /**
    * 履歴ID
    */
    private String historyID;
    
    /**
    * マスタ基準日
    */
    private String date;
    

}
