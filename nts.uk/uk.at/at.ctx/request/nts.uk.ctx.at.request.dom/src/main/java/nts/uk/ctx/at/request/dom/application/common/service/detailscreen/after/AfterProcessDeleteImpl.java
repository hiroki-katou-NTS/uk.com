package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.AgentAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.AgentPubImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverRepresenterImport;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.other.DestinationJudgmentProcess;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;

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
	private AfterApprovalProcess detailedScreenAfterApprovalProcessService;
	
	@Inject
	private AfterProcessDelete DetailedScreenProcessAfterDeleteSevice;

	@Inject
	private AgentAdapter approvalAgencyInformationService;
	
	@Inject
	private DestinationJudgmentProcess destinationJudgmentProcessService;
	
	@Inject
	private  EmployeeAdapter  employeeAdapter;
	
	@Inject
	private AppTypeDiscreteSettingRepository  appTypeDiscreteSettingRepo;
	
	@Inject
	private ApplicationRepository applicationRepo;
	
	
	
	@Override
	public List<String> screenAfterDelete(String companyID,String appID) {
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
				List<String> listApproverID = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), appApprovalPhase.getApprovalATR());
				
				if (!listApproverID.isEmpty()) {
					List<String> approver = new ArrayList<String>();
					
					/** 3-1 アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)*/
					AgentPubImport agentPubImport = approvalAgencyInformationService.getApprovalAgencyInformation(companyID, approver);
					List<ApproverRepresenterImport> listApproverRepresenter = agentPubImport.getListApproverAndRepresenterSID();
					
					/** 3-2 */
					listDestination = destinationJudgmentProcessService.getDestinationJudgmentProcessService(listApproverRepresenter);
					
					//Add listDestination to listSender
					List<String> listSender = new ArrayList<String>(listDestination);
					listSender.addAll(listApproverID);
					if(appApprovalPhase.getApprovalATR() != ApprovalAtr.APPROVED){
						break;
					}					
				}
			}
		}
		//filter duplicate
		 converList = listDestination.stream().distinct().collect(Collectors.toList());
		
		if (converList != null) {
			// TODOgui mail cho ng xac nhan
			
			// TODO lay thong tin Imported
			List<String> lstMail = new ArrayList<>();
			for(String employeeId: converList){
				String mail = employeeAdapter.getEmployeeInfor(employeeId).getCompanyMail();
				lstMail.add(mail);
			}
		}

		//TODO delete domaim Application
		applicationRepo.deleteApplication(companyID, appID);
		//TODO hien thi thong tin Msg_16 
		if (converList != null) {
			//TODO Hien thi thong tin 392
		}
		return converList;
	}

}
