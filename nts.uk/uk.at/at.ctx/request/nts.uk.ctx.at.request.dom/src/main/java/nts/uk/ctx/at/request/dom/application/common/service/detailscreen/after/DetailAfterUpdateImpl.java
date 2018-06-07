package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverApprovedImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverWithFlagImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;

@Stateless
public class DetailAfterUpdateImpl implements DetailAfterUpdate {

	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationRepository_New applicationRepository;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	public ProcessResult processAfterDetailScreenRegistration(Application_New application) {
		String companyID = application.getCompanyID();
		List<String> destinationList = new ArrayList<>();
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		
		ApproverApprovedImport_New approverApprovedImport = approvalRootStateAdapter.getApproverApproved(application.getAppID());
		
		approvalRootStateAdapter.doReleaseAllAtOnce(companyID, application.getAppID());
			
		Application_New application_New = applicationRepository.findByID(companyID, application.getAppID()).get();
		
		// ドメインモデル「申請」と紐付き「承認フェーズ」「承認枠」「反映情報」をUpdateする
		application_New.setReversionReason(new AppReason(""));
		application_New.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
		applicationRepository.update(application_New);
		
		// 承認を行った承認者一覧に項目があるかチェックする
		if (CollectionUtil.isEmpty(approverApprovedImport.getListApprover())) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, application.getAppID());
		}
		
		// 承認を行った承認者一覧に項目がある ( There is an item in the approver list that made approval )
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする ( Domain model "Application type setting". Check to send mail automatically when newly registered )
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value);
		if (appTypeDiscreteSettingOp.get().getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN)) {
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, application.getAppID());
		}
		isAutoSendMail = true;
		// 「申請種類別設定」．新規登録時に自動でメールを送信するがtrue ( "Setting by application type". Automatically send mail when new registration is true )
		// 承認を行った承認者一覧を先頭から最後までループする ( Loop from the top to the end of the approver list that gave approval )
		for (ApproverWithFlagImport_New approverWithFlagImport : approverApprovedImport.getListApproverWithFlagOutput()) {
			// 承認を行った承認者の代行者フラグをチェックする ( Check the agent's flag of the approver who made the approval )
			String loopID = approverWithFlagImport.getEmployeeID(); 
			if (approverWithFlagImport.getAgentFlag().equals(Boolean.TRUE)) {
				AgentPubImport agentPubImport = approvalRootStateAdapter.getApprovalAgencyInformation(companyID, Arrays.asList(loopID));
				// 代行の権限を持っているかチェックする ( Check if you have delegation authority )
				if(agentPubImport.getListRepresenterSID().contains(loopID)) {
					destinationList.add(loopID);
				}
			} else {
				// ループ中承認者を送信先リストに追加する ( Add an approver in the loop to the destination list )
				destinationList.add(loopID);
			}
		}
		
		// 送信先リストに重複な項目を消す ( Delete duplicate items in the destination list )
		destinationList.stream().distinct().collect(Collectors.toList());
		
		// 送信先リストに項目がいるかチェックする ( Check if there is an item in the destination list )
		if(!CollectionUtil.isEmpty(destinationList)){
			// 送信先リストにメールを送信する ( Send mail to recipient list )
			MailResult mailResult = otherCommonAlgorithm.sendMailApproverApprove(destinationList, application_New);
			autoSuccessMail = mailResult.getSuccessList();
			autoFailMail = mailResult.getFailList();
		}
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, application.getAppID());
	}
}
