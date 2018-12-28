package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class PerProcessClsSetCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * ユーザID
    */
    private String uid;
    

}
