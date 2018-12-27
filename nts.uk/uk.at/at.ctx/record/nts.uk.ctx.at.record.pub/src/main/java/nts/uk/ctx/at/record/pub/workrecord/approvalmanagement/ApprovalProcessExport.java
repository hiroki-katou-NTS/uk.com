package nts.uk.ctx.at.record.pub.workrecord.approvalmanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.enums.ConfirmationOfManagerOrYouself;

@AllArgsConstructor
@Getter
public class ApprovalProcessExport {

	private String companyId;

	private Boolean useDayApproverConfirm;

	private Boolean useMonthApproverConfirm;

	private List<String> lstJobTitleNotUse;

	private ConfirmationOfManagerOrYouself supervisorConfirmErrorAtr;
	
	public static ApprovalProcessExport fromDomain(ApprovalProcessingUseSetting domain){
		
        return new ApprovalProcessExport(domain.getCompanyId(), domain.getUseDayApproverConfirm(), domain.getUseMonthApproverConfirm(), domain.getLstJobTitleNotUse(), domain.getSupervisorConfirmErrorAtr());
    }
}
