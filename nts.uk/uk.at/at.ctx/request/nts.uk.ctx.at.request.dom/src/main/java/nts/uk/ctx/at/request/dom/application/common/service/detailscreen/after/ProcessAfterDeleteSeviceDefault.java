package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ScreenAfterDelete;
import nts.uk.ctx.at.request.dom.application.common.service.other.ApprovalAgencyInformationService;
import nts.uk.ctx.at.request.dom.application.common.service.other.DestinationJudgmentProcessService;
import nts.uk.ctx.at.request.dom.application.common.service.other.dto.ApprovalAgencyInformationOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.dto.ObjApproverRepresenter;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
/** 5-2.詳細画面削除後の処理*/
public class ProcessAfterDeleteSeviceDefault implements ProcessAfterDeleteSevice {

	@Inject
	private AppApprovalPhaseRepository appApprovalPhaseRepository;

	@Inject
	private AfterApprovalProcessService detailedScreenAfterApprovalProcessService;
	
	@Inject
	private ProcessAfterDeleteSevice DetailedScreenProcessAfterDeleteSevice;

	@Inject
	private ApprovalAgencyInformationService approvalAgencyInformationService;
	
	@Inject
	private DestinationJudgmentProcessService destinationJudgmentProcessService;
	
	
	@Override
	public ScreenAfterDelete screenAfterDelete(Application applicationData, String appID) {
		String companyID = AppContexts.user().companyId();
		AppCanAtr sendMailWhenApprovalFlg = null;
		ApprovalAtr approvalAtr = null;
		
		List<String> listDestination = new ArrayList<String>();
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする(kiểm tra
		// 「申請種類別設定」．新規登録時に自動でメールを送信する)//
		if (sendMailWhenApprovalFlg == AppCanAtr.CAN) {
			/**
			 * ドメインモデル「申請」．「承認フェーズ」1～5の順でループする(loop xử lý theo thứ tự
			 * domain「申請」．「承認フェーズ」1～5)
			 */
			List<AppApprovalPhase> listAppApprovalPhase = appApprovalPhaseRepository.findPhaseByAppID(companyID, applicationData.getApplicationID());
			for (AppApprovalPhase appApprovalPhase : listAppApprovalPhase) {
				// 8-2.3.1
				List<String> listApproverID = detailedScreenAfterApprovalProcessService.actualReflectionStateDecision(appApprovalPhase.getAppID(), appApprovalPhase.getPhaseID(), appApprovalPhase.getApprovalATR());
				
				if (!listApproverID.isEmpty()) {
					List<String> approver = new ArrayList<String>();
					
					/** 3-1 アルゴリズム「承認代行情報の取得処理」を実行する(thực hiện xử lý 「承認代行情報の取得処理」)*/
					ApprovalAgencyInformationOutput approvalAgencyInformationOutput = approvalAgencyInformationService.getApprovalAgencyInformation(companyID, approver);
					List<ObjApproverRepresenter> listApproverRepresenter = approvalAgencyInformationOutput.getListApproverAndRepresenterSID();
					
					/** 3-2 */
					listDestination = destinationJudgmentProcessService.getDestinationJudgmentProcessService(listApproverRepresenter);
					/*
					//Add listDestination to listSender
					List<String> listSender = new ArrayList<String>(listDestination);
					listSender.addAll(listApproverID);*/
					if(approvalAtr != ApprovalAtr.APPROVED){
						break;
					}					
				}
			}
		}
		//filter duplicate
		List<String> converList = listDestination.stream().distinct().collect(Collectors.toList());
		if (converList != null) {
			// TODOgui mail cho ng xac nhan
			// TODO lay thong tin Imported
		}

		//TODO delete domaim Application
		
		//TODO hien thi thong tin Msg_16 
		if (converList != null) {
			//TODO Hien thi thong tin 392
		}
		return null;
	}

}
