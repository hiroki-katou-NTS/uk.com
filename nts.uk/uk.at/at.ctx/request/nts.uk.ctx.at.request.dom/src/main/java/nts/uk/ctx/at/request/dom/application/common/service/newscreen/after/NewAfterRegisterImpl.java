package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterApprovalProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.DestinationJudgmentProcess;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

@Stateless
public class NewAfterRegisterImpl implements NewAfterRegister {
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;
	
	@Inject
	private AfterApprovalProcess detailedScreenAfterApprovalProcessService;
	
	@Inject
	private AgentAdapter approvalAgencyInformationService;
	
	@Inject
	private DestinationJudgmentProcess destinationJudgmentProcessService;
	
	@Inject
	private MailSender mailSender;
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	
	public List<String> processAfterRegister(Application application){
		
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする ( Domain model "Application type setting". Check to send mail automatically when newly registered )
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(application.getCompanyID(), application.getApplicationType().value);
		if(!appTypeDiscreteSettingOp.isPresent()) {
			throw new RuntimeException("Not found AppTypeDiscreteSetting in table KRQST_APP_TYPE_DISCRETE, appType =" + application.getApplicationType().value);
		}
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		if(appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN)) {
			return Collections.emptyList();
		}
		// アルゴリズム「送信先リストの取得」を実行する  ( Execute the algorithm "Acquire destination list" )
		List<String> destinationList = acquireDestinationList(application);
		
		// 送信先リストに項目がいるかチェックする ( Check if there is an item in the destination list )
		if(destinationList.size() < 1) return Collections.emptyList();
		List<String> destinationMails = new ArrayList<>();
		for(String destination : destinationList) {
			// sendMail(obj);
			// Imported(Employment)[Employee]; // Imported(就業)「社員」 ??? 
			String email = employeeAdapter.empEmail(destination);
			try {
				if(!Strings.isBlank(email)) {
					mailSender.send("nts", email, new MailContents("nts mail", "new mail from NTS"));
					destinationMails.add(email + System.lineSeparator());
				}
				
			} catch (SendMailFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return destinationMails;
	}
	
	public List<String> acquireDestinationList(Application application){
		List<String> destinationList = new ArrayList<>();
		
		// ドメインモデル「反映情報」．実績反映状態をチェックする ( Domain model "reflection information". Check actual reflection status )
		if(application.getReflectPerState().equals(ReflectPlanPerState.REFLECTED)) {
			return destinationList;
		}
		List<AppApprovalPhase> appApprovalPhases = application.getListPhase();
		// 承認フェーズ1～5のループ ( Loop of approval phases 1 to 5 )
		for(int i = 0; i < appApprovalPhases.size(); i++) {
			AppApprovalPhase appApprovalPhase = appApprovalPhases.get(i);
			// 「承認フェーズ」．承認区分をチェックする ( "Approval phase". Check approval indicator )
			if(appApprovalPhase.getApprovalATR().equals(ApprovalAtr.DENIAL)||appApprovalPhase.getApprovalATR().equals(ApprovalAtr.REMAND)){
				break;
			} else if(appApprovalPhase.getApprovalATR().equals(ApprovalAtr.APPROVED)) {
				continue;
			} else {
				// アルゴリズム「L」を実行する ( Execute algorithm "Acquire approver list" ) 
				/*List<String> apPhases = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
								application.getApplicationID(), 
								appApprovalPhase.getPhaseID(), 
								ApprovalAtr.APPROVED);
				if(apPhases.size() < 1 ) continue;*/
				
				// アルゴリズム「未承認の承認者一覧を取得する」を実行する ( Execute algorithm "Acquire unapproved approver list" )
				List<String> unApPhases = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(
								application.getApplicationID(), 
								appApprovalPhase.getPhaseID(), 
								ApprovalAtr.UNAPPROVED);
				
				// 未承認の承認者一覧(output)に承認者がいるかチェックする ( Check whether there is an approver in the unapproved approver list (output) )
				/*boolean existFlag = false;
				for(int x = 0; x < unApPhases.size(); x++){
					String unApPhase = unApPhases.get(x);
					for(int y = 0; y < apPhases.size(); y++) {
						String apPhase = apPhases.get(y);
						if(unApPhase.equals(apPhase)) {
							existFlag = true;
							break;
						}
					}
					if(existFlag) {
						break;
					}
				}*/
				
				// 未承認の承認者一覧に承認者がいない ( Approver does not exist in unapproved approver list )
				/*if(existFlag) {
					break;
				}*/
				
				// アルゴリズム「承認代行情報の取得処理」を実行する ( Executes the algorithm "acquisition process of approval substitution information" )
				AgentPubImport agencyInformationOutput = approvalAgencyInformationService.getApprovalAgencyInformation(appApprovalPhase.getCompanyID(), unApPhases);
				
				// アルゴリズム「送信先の判断処理」を実行する ( Executes the algorithm "destination determination process" )
				List<String> result = destinationJudgmentProcessService.getDestinationJudgmentProcessService(agencyInformationOutput.getListApproverAndRepresenterSID());
				destinationList.addAll(result);
				break;
			}
		}
		destinationList.stream().distinct().collect(Collectors.toList());
		return destinationList;
	}
}
