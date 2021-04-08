package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
public class NewAfterRegisterImpl implements NewAfterRegister {
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationAlgorithm applicationAlgorithm;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	public ProcessResult processAfterRegister(List<String> appIDLst, AppTypeSetting appTypeSetting, boolean mailServerSet, boolean multiPeopleFlg){
		ProcessResult processResult = new ProcessResult();
		processResult.setProcessDone(true);
		processResult.setAppIDLst(appIDLst);
		// アルゴリズム「登録処理時のメール自動送信するか判定」を実行するthực hiện thuật toán "kiểm tra xem ở xử lý đăng ký có gửi mail tự đọng"
		SendMailAtr sendMailAtr = applicationAlgorithm.checkAutoSendMailRegister(appTypeSetting, mailServerSet);
		if(sendMailAtr == SendMailAtr.NOT_SEND) {
			return processResult;
		}
		processResult.setAutoSendMail(true);
		List<String> loopAppIDLst = new ArrayList<>();
		if(multiPeopleFlg) {
			loopAppIDLst = appIDLst;
		} else {
			loopAppIDLst = Arrays.asList(appIDLst.get(0));
		}
		for(String loopAppID : loopAppIDLst) {
			// Imported(就業)「社員」を取得する(lấy thông tin Imported(就業)「社員」) 
			List<String> destinationList = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(loopAppID, 5);
			if(CollectionUtil.isEmpty(destinationList)) {
				continue;
			}
			Application application = applicationRepository.findByID(loopAppID).get();
			// 承認者へ送る（新規登録、更新登録、承認）//Gửi đến người approve(đăng ký mới, đăng ký update, approve)
			MailResult mailResult = otherCommonAlgorithm.sendMailApproverApprove(destinationList, application);
			processResult.setAutoSuccessMail(mailResult.getSuccessList());
			processResult.setAutoFailMail(mailResult.getFailList());
			processResult.setAutoFailServer(mailResult.getFailServerList());
		}
		processResult.setAutoSuccessMail(processResult.getAutoSuccessMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailMail(processResult.getAutoFailMail().stream().distinct().collect(Collectors.toList()));
		processResult.setAutoFailServer(processResult.getAutoFailServer().stream().distinct().collect(Collectors.toList()));
		return processResult;
	}
}
