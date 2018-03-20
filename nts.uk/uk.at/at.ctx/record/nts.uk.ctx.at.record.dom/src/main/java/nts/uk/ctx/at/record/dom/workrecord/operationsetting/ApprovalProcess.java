package nts.uk.ctx.at.record.dom.workrecord.operationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
* 承認処理の利用設定
*/
@AllArgsConstructor
@Getter
public class ApprovalProcess extends AggregateRoot
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
    private YourselfConfirmError supervisorConfirmError;
    
    public static ApprovalProcess createFromJavaType(String cid, String jobTitleId, int useDayApproverConfirm, int useMonthApproverComfirm, int supervisorConfirmError)
    {
        ApprovalProcess  approvalProcess =  new ApprovalProcess(cid, jobTitleId, useDayApproverConfirm, useMonthApproverComfirm,  EnumAdaptor.valueOf(supervisorConfirmError, YourselfConfirmError.class));
        return approvalProcess;
    }
    
}
