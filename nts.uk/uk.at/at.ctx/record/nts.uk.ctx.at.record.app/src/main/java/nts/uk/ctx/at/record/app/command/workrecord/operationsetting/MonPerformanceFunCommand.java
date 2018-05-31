package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;

@Value
public class MonPerformanceFunCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コメント
    */
    private String comment;
    
    /**
    * 日別の本人確認を表示する
    */
    private boolean dailySelfChkDispAtr;
    
    private Long version;

}
