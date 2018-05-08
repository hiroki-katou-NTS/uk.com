package nts.uk.ctx.at.record.app.command.workrecord.operationsetting;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

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
    * 上司確認を利用する
    */
    private int useDayApproverConfirm;
    
    /**
    * 月の承認者確認を利用する
    */
    private int useMonthApproverComfirm;
    
    /**
    * エラーがある場合の上司確認
    */
    private int supervisorConfirmError;
    
    private Long version;

}
