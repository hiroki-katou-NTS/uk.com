package nts.uk.ctx.at.record.pubimp.workrecord.approvalmanagement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.pub.workrecord.approvalmanagement.ApprovalProcessExport;
import nts.uk.ctx.at.record.pub.workrecord.approvalmanagement.ApprovalProcessPub;

/**
 * 
 * @author thuongtv
 *
 */

@Stateless
public class ApprovalProcessPubImpl implements ApprovalProcessPub {
	@Inject
    private ApprovalProcessRepository finder;
	
	@Override
	public ApprovalProcessExport getApprovalProcess(String cid) {
		Optional<ApprovalProcess> approvalProcess = finder.getApprovalProcessById(cid);
		if(approvalProcess.isPresent())
		return ApprovalProcessExport.fromDomain(approvalProcess.get());
		return null;
	}

}
