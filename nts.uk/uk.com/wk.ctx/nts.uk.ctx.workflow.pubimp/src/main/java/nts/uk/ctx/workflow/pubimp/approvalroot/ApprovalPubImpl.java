package nts.uk.ctx.workflow.pubimp.approvalroot;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;

@Stateless
public class ApprovalPubImpl implements ApprovalRootPub{
	
	@Inject
	private WorkAppApprovalRootRepository appRootRepository;
	
	@Override
	public void getApprovalRootOfSubjectRequest(String cid, String sid, Date standardDate, String subjectRequest) {
//		this.appRootRepository.getPsApprovalRoot(companyId, approvalId, employeeId, historyId);
	}
	
}
