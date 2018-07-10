package nts.uk.ctx.at.request.dom.application.common.service.newscreen.after;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.apptypediscretesetting.AppTypeDiscreteSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.AppCanAtr;

@Stateless
public class NewAfterRegisterImpl_New implements NewAfterRegister_New {
	
	@Inject
	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	public ProcessResult processAfterRegister(Application_New application){
		boolean isProcessDone = true;
		boolean isAutoSendMail = false;
		List<String> autoSuccessMail = new ArrayList<>();
		List<String> autoFailMail = new ArrayList<>();
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする
		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(application.getCompanyID(), application.getAppType().value);
		if(!appTypeDiscreteSettingOp.isPresent()) {
			throw new RuntimeException("Not found AppTypeDiscreteSetting in table KRQST_APP_TYPE_DISCRETE, appType =" + application.getAppType().value);
		}
		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
		if(appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN)){
			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, application.getAppID(),""); 
		}
		isAutoSendMail = true;
		// アルゴリズム「送信先リストの取得」を実行する
		List<String> destinationList = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(
				application.getCompanyID(), 
				application.getAppID(), 
				1, 
				true, 
				application.getEmployeeID(), 
				application.getAppType().value, 
				application.getAppDate());
		
		// 送信先リストに項目がいるかチェックする 
		if(!CollectionUtil.isEmpty(destinationList)){
			MailResult mailResult = otherCommonAlgorithm.sendMailApproverApprove(destinationList, application);
			autoSuccessMail = mailResult.getSuccessList();
			autoFailMail = mailResult.getFailList();
		}
		return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, application.getAppID(),"");
	}
}
