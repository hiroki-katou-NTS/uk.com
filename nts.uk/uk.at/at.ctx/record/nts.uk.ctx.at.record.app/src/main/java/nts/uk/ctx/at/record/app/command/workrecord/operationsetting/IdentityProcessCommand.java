package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;

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
    private boolean useDailySelfCk;
    
    /**
    * 月の本人確認を利用する
    */
    private boolean useMonthSelfCK;
    
    /**
    * エラーがある場合の本人確認
    */
    private Integer yourselfConfirmError;
    
    private Long version;

}
