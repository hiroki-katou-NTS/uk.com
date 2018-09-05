package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService_New;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ProcessDeleteResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;

/**
 * 
 * @author hieult
 *
 */
@Stateless
/** 5-2.詳細画面削除後の処理*/
public class AfterProcessDeleteImpl implements AfterProcessDelete {
	
	@Inject
	private AppTypeDiscreteSettingRepository  appTypeDiscreteSettingRepo;
	
	@Inject
	private ApplicationRepository_New applicationRepo;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private ApplicationApprovalService_New applicationApprovalService;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Override
	public ProcessDeleteResult screenAfterDelete(String companyID,String appID, Long version) {
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		Application_New application = applicationRepo.findByID(companyID, appID).get();
		ApplicationType appType = application.getAppType();
		AppCanAtr sendMailWhenApprovalFlg = appTypeDiscreteSettingRepo.getAppTypeDiscreteSettingByAppType(companyID, appType.value)
				.get().getSendMailWhenRegisterFlg();
		List<String> converList = new ArrayList<String>();
		
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする(kiểm tra
		// 「申請種類別設定」．新規登録時に自動でメールを送信する)//
		if (sendMailWhenApprovalFlg == AppCanAtr.CAN) {
			isAutoSendMail = true;
			converList = approvalRootStateAdapter.getMailNotifierList(companyID, appID);
			if(!CollectionUtil.isEmpty(converList)){
				MailResult mailResult = otherCommonAlgorithm.sendMailApproverDelete(converList, application);
				autoSuccessMail = mailResult.getSuccessList();
				autoFailMail = mailResult.getFailList();
			}
		}
		//TODO delete domaim Application
		applicationApprovalService.delete(companyID, appID, version, appType);
		//TODO hien thi thong tin Msg_16 
		/*if (converList != null) {
			//TODO Hien thi thong tin 392
		}*/
		
		// 暫定データの登録
		interimRemainDataMngRegisterDateChange.registerDateChange(
				companyID, 
				application.getEmployeeID(), 
				Arrays.asList(application.getAppDate()));
		return new ProcessDeleteResult(
				new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, appID,""),
				appType);
	}

}
