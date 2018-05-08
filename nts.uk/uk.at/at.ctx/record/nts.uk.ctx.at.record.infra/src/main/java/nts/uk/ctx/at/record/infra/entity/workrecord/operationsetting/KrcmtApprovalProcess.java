package nts.uk.ctx.at.record.infra.entity.workrecord.operationsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.YourselfConfirmError;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 承認処理の利用設定
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_APPROVAL_PROCESS")
public class KrcmtApprovalProcess extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public KrcmtApprovalProcessPk approvalProcessPk;
    
    /**
    * 職位ID
    */
    @Basic(optional = true)
    @Column(name = "JOB_TITLE_ID")
    public String jobTitleId;
    
    /**
    * 上司確認を利用する
    */
    @Basic(optional = false)
    @Column(name = "USE_DAY_APPROVER_CONFIRM")
    public int useDayApproverConfirm;
    
    /**
    * 月の承認者確認を利用する
    */
    @Basic(optional = false)
    @Column(name = "USE_MONTH_APPROVER_COMFIRM")
    public int useMonthApproverComfirm;
    
    /**
    * エラーがある場合の上司確認
    */
    @Basic(optional = true)
    @Column(name = "SUPERVISOR_CONFIRM_ERROR")
    public int supervisorConfirmError;
    
    @Override
    protected Object getKey()
    {
        return approvalProcessPk;
    }

    public ApprovalProcess toDomain() {
        return new ApprovalProcess(this.approvalProcessPk.cid, this.jobTitleId, this.useDayApproverConfirm, this.useMonthApproverComfirm, EnumAdaptor.valueOf(this.supervisorConfirmError, YourselfConfirmError.class));
    }
    public static KrcmtApprovalProcess toEntity(ApprovalProcess domain) {
        return new KrcmtApprovalProcess(new KrcmtApprovalProcessPk(domain.getCid()), domain.getJobTitleId(), domain.getUseDayApproverConfirm(), domain.getUseMonthApproverComfirm(), domain.getSupervisorConfirmError().value);
    }

}
