package nts.uk.ctx.at.record.pubimp.workrecord.approvalmanagement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalProcessingUseSetting;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalProcessingUseSettingRepository;
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
    private ApprovalProcessingUseSettingRepository finder;
	
	@Override
	public ApprovalProcessExport getApprovalProcess(String cid) {
		Optional<ApprovalProcessingUseSetting> approvalProcess = finder.findByCompanyId(cid);
		if(approvalProcess.isPresent())
		return ApprovalProcessExport.fromDomain(approvalProcess.get());
		return null;
	}

}
