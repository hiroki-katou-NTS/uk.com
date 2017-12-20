package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.mail.send.MailContents;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRepresenterImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.other.DestinationJudgmentProcess;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.mail.MailSender;
import nts.uk.shr.com.mail.SendMailFailedException;

/**
 * 
 * @author hieult
 *
 */
@Stateless
/** 5-2.詳細画面削除後の処理*/
public class AfterProcessDeleteImpl implements AfterProcessDelete {

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Inject
	private AgentAdapter approvalAgencyInformationService;
	
	@Inject
	private DestinationJudgmentProcess destinationJudgmentProcessService;
	
	@Inject
	private  EmployeeRequestAdapter  employeeAdapter;
	
	@Inject
	private AppTypeDiscreteSettingRepository  appTypeDiscreteSettingRepo;
	
	@Inject
	private ApplicationRepository applicationRepo;
	
	@Inject
	private MailSender mailSender;
	
	@Override
	public String screenAfterDelete(String companyID,String appID, Long version) {
		ApplicationType appType = applicationRepo.getAppById(companyID, appID).get().getApplicationType();
		AppCanAtr sendMailWhenApprovalFlg = appTypeDiscreteSettingRepo.getAppTypeDiscreteSettingByAppType(companyID, appType.value)
				.get().getSendMailWhenRegisterFlg();
		List<String> converList =new ArrayList<String>();
	
		
		List<String> listDestination = new ArrayList<String>();
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする(kiểm tra
		// 「申請種類別設定」．新規登録時に自動でメールを送信する)//
		if (sendMailWhenApprovalFlg == AppCanAtr.CAN) {
			/**
			 * ドメインモデル「申請」．「承認フェーズ」1～5の順でループする(loop xử lý theo thứ tự
			 * domain「申請」．「承認フェーズ」1～5)
			 */
			List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID, appID);
			for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
				// 8-2.3.1
				List<String> listApproverID = new ArrayList<>();// detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), appApprovalPhase.getApprovalATR());
				appApprovalPhase.getListFrame().stream().forEach(x -> {
					x.getListApproveAccepted().stream().forEach(y ->{
						listApproverID.add(y.getApproverSID());
					});
				});
				if (!listApproverID.isEmpty()) {
					//List<String> approver = new ArrayList<String>();
					
					/** 3-1 アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)*/
					AgentPubImport agentPubImport = approvalAgencyInformationService.getApprovalAgencyInformation(companyID, listApproverID);
					//承認者の代行情報リスト
					List<ApproverRepresenterImport> listApproverRepresenter = agentPubImport.getListApproverAndRepresenterSID();
					
					/** 3-2.送信先の判断処理 */
					listDestination.addAll(destinationJudgmentProcessService.getDestinationJudgmentProcessService(listApproverRepresenter));
					
					/*//Add listDestination to listSender
					List<String> listSender = new ArrayList<String>(listDestination);
					listSender.addAll(listApproverID);*/
					if(appApprovalPhase.getApprovalATR() != ApprovalAtr.APPROVED){
						break;
					}					
				}
			}
		}
		//filter duplicate
		converList = listDestination.stream().distinct().collect(Collectors.toList());
		String strMail = "";
		if (converList != null) {
			// TODOgui mail cho ng xac nhan
			
			// TODO lay thong tin Imported
			
			for(String employeeId: converList){
				String mail = employeeAdapter.getEmployeeInfor(employeeId).getSMail();
				if(Strings.isBlank(mail)) {
					continue;
				}
				try {
					mailSender.send("nts", mail, new MailContents("nts mail", "delete mail from NTS"));
					strMail += mail + System.lineSeparator();
				} catch (SendMailFailedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		//TODO delete domaim Application
		applicationRepo.deleteApplication(companyID, appID, version);
		//TODO hien thi thong tin Msg_16 
		/*if (converList != null) {
			//TODO Hien thi thong tin 392
		}*/
		return strMail;
	}

}
