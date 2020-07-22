package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.SendMailAtr;
import nts.uk.ctx.at.request.dom.application.algorithm.ApplicationAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;

@Stateless
public class NewAfterRegisterImpl_New implements NewAfterRegister_New {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationAlgorithm applicationAlgorithm;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	public ProcessResult processAfterRegister(String appID, AppTypeSetting appTypeSetting, boolean mailServerSet){
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		// アルゴリズム「登録処理時のメール自動送信するか判定」を実行するthực hiện thuật toán "kiểm tra xem ở xử lý đăng ký có gửi mail tự đọng"
		SendMailAtr sendMailAtr = applicationAlgorithm.checkAutoSendMailRegister(appID, appTypeSetting, mailServerSet);
		if(sendMailAtr == SendMailAtr.NOT_SEND) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, ""); 
		}
		isAutoSendMail = true;
		// Imported(就業)「社員」を取得する(lấy thông tin Imported(就業)「社員」) 
		List<String> destinationList = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(appID, 5);
		if(CollectionUtil.isEmpty(destinationList)) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, ""); 
		}
		Application application = applicationRepository.findByID(appID).get();
		// 承認者へ送る（新規登録、更新登録、承認）//Gửi đến người approve(đăng ký mới, đăng ký update, approve)
		MailResult mailResult = otherCommonAlgorithm.sendMailApproverApprove(destinationList, application);
		autoSuccessMail = mailResult.getSuccessList();
		autoFailMail = mailResult.getFailList();
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID, "");
	}
}
