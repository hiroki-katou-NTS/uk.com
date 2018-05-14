package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;

@Value
public class ApprovalProcessCommand
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
     * 職位ID
     */
     private String jobTitleId;
     
    /**
    * 日の承認者確認を利用する
    */
    private boolean useDailyBossChk;
    
    /**
    * 月の承認者確認を利用する
    */
    private boolean useMonthBossChk;
    
    /**
    * エラーがある場合の上司確認
    */
    private Integer supervisorConfirmError;
    
    private Long version;

}
