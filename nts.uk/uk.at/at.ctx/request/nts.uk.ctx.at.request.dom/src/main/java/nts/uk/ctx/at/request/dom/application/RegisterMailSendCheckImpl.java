package nts.uk.ctx.at.request.dom.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;

/**
 * 就業確定済みかのチェック
 *
 */
@Stateless
public class RegisterMailSendCheckImpl implements RegisterMailSendCheck {

	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

//	@Inject
//	private AppTypeDiscreteSettingRepository appTypeDiscreteSettingRepository;

	@Override
	public ProcessResult sendMail(Application application) {
		ProcessResult processResult = new ProcessResult();
		processResult.setProcessDone(true);
		List<String> destinationList = new ArrayList<>();
		// ドメインモデル「申請種類別設定」．新規登録時に自動でメールを送信するをチェックする
//		Optional<AppTypeDiscreteSetting> appTypeDiscreteSettingOp = appTypeDiscreteSettingRepository.getAppTypeDiscreteSettingByAppType(companyID, application.getAppType().value);
//		if(!appTypeDiscreteSettingOp.isPresent()) {
//			throw new RuntimeException("Not found AppTypeDiscreteSetting in table KRQST_APP_TYPE_DISCRETE, appType =" + application.getAppType().value);
//		}
//		AppTypeDiscreteSetting appTypeDiscreteSetting = appTypeDiscreteSettingOp.get();
//		if(appTypeDiscreteSetting.getSendMailWhenRegisterFlg().equals(AppCanAtr.NOTCAN)){
//			return new ProcessResult(isProcessDone, isAutoSendMail, autoSuccessMail, autoFailMail, autoFailServer, application.getAppID(),"");
//		}
		processResult.setAutoSendMail(true);
		// アルゴリズム「送信先リストの取得」を実行する
		destinationList = approvalRootStateAdapter.getNextApprovalPhaseStateMailList(
				application.getAppID(), 
				1);
		
		// 送信先リストに項目がいるかチェックする 
		if(!CollectionUtil.isEmpty(destinationList)){
			MailResult mailResult = otherCommonAlgorithm.sendMailApproverApprove(destinationList, application, "");
			processResult.setAutoSuccessMail(mailResult.getSuccessList());
			processResult.setAutoFailMail(mailResult.getFailList());
			processResult.setAutoFailServer(mailResult.getFailServerList());
		}
		processResult.setAppID(application.getAppID());
		return processResult;
	}
}