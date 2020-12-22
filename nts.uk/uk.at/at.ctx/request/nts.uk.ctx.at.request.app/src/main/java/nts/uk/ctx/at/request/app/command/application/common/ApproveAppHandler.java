package nts.uk.ctx.at.request.app.command.application.common;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterApproval;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ProcessApprovalOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApproveProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class ApproveAppHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ApproveProcessResult> {

	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject
	private DetailAfterApproval detailAfterApproval;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Override
	protected ApproveProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppDetailBehaviorCmd cmd = context.getCommand(); 
		String memo = cmd.getMemo();
		AppDispInfoStartupOutput appDispInfoStartupOutput = cmd.getAppDispInfoStartupOutput().toDomain();
		AppDetailScreenInfo appDetailScreenInfo = appDispInfoStartupOutput.getAppDetailScreenInfo().get();
		Application application = appDetailScreenInfo.getApplication();
//		List<ListOfAppTypes> listOfAppTypes =  cmd.getListOfAppTypes().stream().map(x -> x.toDomain()).collect(Collectors.toList());
		return approve(companyID, application.getAppID(), application, appDispInfoStartupOutput, memo, Collections.emptyList());
	}
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.共通ユースケース.承認する
	 * @param companyID
	 * @param appID
	 * @param application
	 * @param appDispInfoStartupOutput
	 * @param memo
	 * @return
	 */
	public ApproveProcessResult approve(String companyID, String appID, Application application, AppDispInfoStartupOutput appDispInfoStartupOutput, String memo,
			List<ListOfAppTypes> listOfAppTypes) {
		ApproveProcessResult approveProcessResult = new ApproveProcessResult();
		//アルゴリズム「排他チェック」を実行する (thực hiện xử lý 「check version」)
        beforeRegisterRepo.exclusiveCheck(companyID, application.getAppID(), application.getVersion());
		
		//8-2.詳細画面承認後の処理
        ProcessApprovalOutput processApprovalOutput = detailAfterApproval.doApproval(companyID, application.getAppID(), application, appDispInfoStartupOutput, memo);
        approveProcessResult.setProcessDone(true);
//        // IF文を参照
// 		AppTypeSetting appTypeSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
// 				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null);
// 		boolean condition = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet() && appTypeSetting.isSendMailWhenApproval();
// 		if(condition) {
// 			approveProcessResult.setAutoSendMail(true);
//			// メニューの表示名を取得する
//			String appName = listOfAppTypes.stream().filter(x -> {
//				boolean conditionFilter = x.getAppType().value==application.getAppType().value;
//				if(application.getAppType()==ApplicationType.STAMP_APPLICATION) {
//					if(application.getOpStampRequestMode().get()==StampRequestMode.STAMP_ADDITIONAL) {
//						conditionFilter = conditionFilter && x.getOpApplicationTypeDisplay().get()==ApplicationTypeDisplay.STAMP_ADDITIONAL;
//					}
//					if(application.getOpStampRequestMode().get()==StampRequestMode.STAMP_ONLINE_RECORD) {
//						conditionFilter = conditionFilter && x.getOpApplicationTypeDisplay().get()==ApplicationTypeDisplay.STAMP_ONLINE_RECORD;
//					}
//				}
//				return condition;
//			}).findAny().map(x -> x.getAppName()).orElse("");
// 			if(!CollectionUtil.isEmpty(processApprovalOutput.getApproverLst())) {
// 				// 承認者へ送る（新規登録、更新登録、承認）
// 	 			MailResult approverLstResult = otherCommonAlgorithm.sendMailApproverApprove(processApprovalOutput.getApproverLst(), application, appName);
// 	 			approveProcessResult.getAutoSuccessMail().addAll(approverLstResult.getSuccessList());
//	 			approveProcessResult.getAutoFailMail().addAll(approverLstResult.getFailList());
//	 			approveProcessResult.getAutoFailServer().addAll(approverLstResult.getFailServerList());
// 			}
// 			if(Strings.isNotBlank(processApprovalOutput.getApplicant())) {
//				// 申請者へ送る（承認）
//	 			MailResult applicantResult = otherCommonAlgorithm.sendMailApplicantApprove(application, appName);
//	 			approveProcessResult.getAutoSuccessMail().addAll(applicantResult.getSuccessList());
//	 			approveProcessResult.getAutoFailMail().addAll(applicantResult.getFailList());
//	 			approveProcessResult.getAutoFailServer().addAll(applicantResult.getFailServerList());
//			}
// 		}
 		approveProcessResult.setAppID(processApprovalOutput.getAppID());
 		approveProcessResult.setReflectAppId(processApprovalOutput.getReflectAppId());
 		return approveProcessResult;
	}

}
