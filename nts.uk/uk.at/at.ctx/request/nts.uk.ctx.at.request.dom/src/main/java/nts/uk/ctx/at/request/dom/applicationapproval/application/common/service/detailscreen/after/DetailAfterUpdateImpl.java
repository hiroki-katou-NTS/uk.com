package nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.applicationapproval.application.Application;
import nts.uk.ctx.at.request.dom.applicationapproval.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted.Reason;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.output.ApproverResult;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.service.detailscreen.output.ApproverWhoApproved;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

@Stateless
public class DetailAfterUpdateImpl implements DetailAfterUpdate {

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Inject
	private ApprovalFrameRepository approvalFrameRepository;
	
	@Inject
	private ApproveAcceptedRepository approveAcceptedRepository;

	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;

	@Inject
	private AgentAdapter approvalAgencyInformationService;

	@Inject
	private AfterApprovalProcess detailedScreenAfterApprovalProcessService;
	
	@Inject
	private MailSender mailSender;

	public void processAfterDetailScreenRegistration(Application application) {
		String companyID = application.getCompanyID();
		List<String> destinationList = new ArrayList<>();
		// ドメインモデル「申請」を取得する ( Acquire the domain model "application" )
		List<AppApprovalPhase> appApprovalPhases = application.getListPhase();
		
		// 承認を行った承認者を取得する ( Acquire approver who approved )
		ApproverResult approverResult = acquireApproverWhoApproved(appApprovalPhases);
		List<ApproverWhoApproved> approverWhoApproveds = approverResult.getApproverWhoApproveds();
		List<String> approvers = approverResult.getApprovers();
		
		// ドメインモデル「申請」と紐付き「承認フェーズ」「承認枠」「反映情報」をUpdateする
		// application.reversionReason = "";
		application.setReflectPerState(ReflectPlanPerState.NOTREFLECTED);
		//applicationRepository.updateApplication(application);
		if(appApprovalPhases == null) return ;
		for (AppApprovalPhase appApprovalPhase : appApprovalPhases) {
			appApprovalPhase.setApprovalATR(ApprovalAtr.UNAPPROVED);
			List<ApprovalFrame> approvalFrames = appApprovalPhase.getListFrame();
			for (ApprovalFrame approvalFrame : approvalFrames) {
				approvalFrame.getListApproveAccepted().forEach(x -> {
					x.changeApprovalATR(ApprovalAtr.UNAPPROVED);
					x.changeApproverSID("");
					x.changeRepresenterSID("");
					x.changeReason(new Reason(""));
					x.changeApprovalDate(null);
					approveAcceptedRepository.updateApproverAccepted(x, approvalFrame.getFrameID());
				});
			}
			appApprovalPhaseRepository.update(appApprovalPhase);
		}
		
		// 承認を行った承認者一覧に項目があるかチェックする
		if (approverWhoApproveds.size() < 1) {
			return;
		}
		
		// 承認を行った承認者一覧に項目がある ( There is an item in the approver list that made approval )
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする ( Domain model "Application type setting". Check to send mail automatically when newly registered )
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, application.getApplicationType().value);
		if (appTypeDiscreteSettingOp.get().getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN)) {
			return;
		}
		
		// 「申請種類別設定」．新規登録時に自動でメールを送信するがtrue ( "Setting by application type". Automatically send mail when new registration is true )
		// 承認を行った承認者一覧を先頭から最後までループする ( Loop from the top to the end of the approver list that gave approval )
		for (ApproverWhoApproved approverWhoApproved : approverWhoApproveds) {
			// 承認を行った承認者の代行者フラグをチェックする ( Check the agent's flag of the approver who made the approval )
			if (approverWhoApproved.isAgentFlag()) {
				// アルゴリズム「承認代行情報の取得処理」を実行する ( Executes the algorithm "acquisition process of approval substitution information" )
				AgentPubImport agencyInformationOutput = approvalAgencyInformationService
						.getApprovalAgencyInformation(companyID, approvers);
				// 代行の権限を持っているかチェックする ( Check if you have delegation authority )
				for (String id : agencyInformationOutput.getListRepresenterSID()) {
					if (id.equals(approverWhoApproved.getApproverAdaptorDto())) {
						// ループ中承認者を送信先リストに追加する ( Add an approver in the loop to the destination list )
						destinationList.add(approverWhoApproved.getApproverAdaptorDto());
					}
				}
			} else {
				// ループ中承認者を送信先リストに追加する ( Add an approver in the loop to the destination list )
				destinationList.add(approverWhoApproved.getApproverAdaptorDto());
			}
		}
		
		// 送信先リストに重複な項目を消す ( Delete duplicate items in the destination list )
		destinationList.stream().distinct().collect(Collectors.toList());
		
		// 送信先リストに項目がいるかチェックする ( Check if there is an item in the destination list )
		if (destinationList.size() >= 1) {
			// 送信先リストにメールを送信する ( Send mail to recipient list )
			// Imported(Employment)[Employee]; // Imported(就業)「社員」を取得する ???
			System.out.println("Send mail to: "+destinationList);
			/*try {
				mailSender.send("NSVC", "", new MailContents("nts","approvalChange"));
			} catch (SendMailFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}

	public ApproverResult acquireApproverWhoApproved(List<AppApprovalPhase> appApprovalPhases){
		ApproverResult approverResult = new ApproverResult(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
		if(appApprovalPhases == null) {
			return approverResult;
		} 
		// ドメインモデル「申請」．「承認フェーズ」1～5の順でループする ( Domain model 'application'. Loop in the order of "approval phase" 1 to 5 )
		for(AppApprovalPhase appApprovalPhase : appApprovalPhases){
			// アルゴリズム「承認者一覧を取得する」を実行する ( Execute algorithm "Acquire approver list" )
			List<String> approverList = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), ApprovalAtr.APPROVED);
			if(!approverList.isEmpty()){
				// 承認枠１～５ループする ( Approval frame 1 to 5 loop )
				for(ApprovalFrame approvalFrame : appApprovalPhase.getListFrame()) {
					approvalFrame.getListApproveAccepted().forEach(x -> {
						// 「承認枠」．承認区分をチェックする ( "Approval frame". Check approval indicator )
						if(!x.getApprovalATR().equals(ApprovalAtr.UNAPPROVED)){
							// 「承認枠」．代行者に社員IDがあるかチェックする ( "Approval frame". Check if the agent has an employee ID )
							if(Strings.isNotEmpty(x.getRepresenterSID())){
								// 承認を行った承認者一覧に(「承認枠」．代行者, true)を追加する ( Add "Approval frame". Substitute person, true) to the approver list that made approval )
								approverResult.getApproverWhoApproveds().add(new ApproverWhoApproved(x.getRepresenterSID(), true));
							} else {
								// 承認を行った承認者一覧に(「承認枠」．承認者, false)を追加する ( Add "Approval frame". Approver, false) to approver list that made approval )
								approverResult.getApproverWhoApproveds().add(new ApproverWhoApproved(x.getApproverSID(), false));
							}
							// 承認者一覧に(「承認枠」．承認者リスト)を追加する ( Add "Approval frame". Approver list to the approver list )
							approverResult.getApprovers().add(x.getApproverSID());
						}	
					});
				}
			}
		}
		return approverResult;
	}
}
