package nts.uk.ctx.at.record.app.find.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;

/**
* 承認処理の利用設定
*/
@AllArgsConstructor
@Value
public class ApprovalProcessDto
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
    
    
    public static ApprovalProcessDto fromDomain(ApprovalProcess domain)
    {
        return new ApprovalProcessDto(domain.getCid(), domain.getJobTitleId(), domain.getUseDayApproverConfirm(), domain.getUseMonthApproverComfirm(), domain.getSupervisorConfirmError().value);
    }
    
}
