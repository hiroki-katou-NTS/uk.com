package nts.uk.ctx.at.function.ac.workrecord.approvalmanagement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement.ApprovalProcessAdapter;
import nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement.ApprovalProcessImport;
import nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement.ConfirmationOfManagerOrYouselfImport;
import nts.uk.ctx.at.record.pub.workrecord.approvalmanagement.ApprovalProcessExport;
import nts.uk.ctx.at.record.pub.workrecord.approvalmanagement.ApprovalProcessPub;

/**
 * 
 * @author thuongtv
 *
 */

@Stateless
public class ApprovalProcessAcFinder implements ApprovalProcessAdapter {

	@Inject
	ApprovalProcessPub approvalProcessPub;

	@Override
	public ApprovalProcessImport getApprovalProcess(String cid) {
		ApprovalProcessExport objecReturn = approvalProcessPub.getApprovalProcess(cid);
		if (objecReturn != null) {
			return new ApprovalProcessImport(objecReturn.getCompanyId(), objecReturn.getUseDayApproverConfirm(),
					objecReturn.getUseMonthApproverConfirm(), objecReturn.getLstJobTitleNotUse(),
					EnumAdaptor.valueOf(objecReturn.getSupervisorConfirmErrorAtr().value,
							ConfirmationOfManagerOrYouselfImport.class));
		} else {
			return null;
		}
	}

}
