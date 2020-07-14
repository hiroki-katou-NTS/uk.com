package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;

/**
 * 新規登録時のメール送信判定
 *
 */
@Stateless
public class NewRegisterMailSendCheckImpl implements NewRegisterMailSendCheck {

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Override
	public ProcessResult sendMail(AppTypeDiscreteSetting discreteSetting, Application_New application, String companyID, String appID, String reflectAppId, String employeeID, Integer phaseNumber) {
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		if (discreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.CAN)) {
			isAutoSendMail = true;
			boolean phaseComplete = approvalRootStateAdapter.isApproveApprovalPhaseStateComplete(companyID, appID, phaseNumber);
			if(phaseComplete){
				List<String> destination = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(
						application.getAppID(), 
						phaseNumber + 1);
				MailResult applicantResult = otherCommonAlgorithm.sendMailApproverApprove(destination, application);
				autoSuccessMail.addAll(applicantResult.getSuccessList());
				autoFailMail.addAll(applicantResult.getFailList());
			}
		}
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, reflectAppId);
	}

	
}
