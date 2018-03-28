package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class IdentityProcessCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 日の本人確認を利用する
    */
    private int useDailySelfCk;
    
    /**
    * 月の本人確認を利用する
    */
    private int useMonthSelfCK;
    
    /**
    * エラーがある場合の本人確認
    */
    private int yourselfConfirmError;
    
    private Long version;

}
