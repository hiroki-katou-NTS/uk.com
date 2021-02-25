package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterDeny;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class DenyAppHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ProcessResult> {
	
	@Inject
	private DetailAfterDeny detailAfterDeny;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Override
	protected ProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		ProcessResult processResult = new ProcessResult();
		AppDetailBehaviorCmd cmd = context.getCommand();
		String memo = cmd.getMemo();
		AppDispInfoStartupOutput appDispInfoStartupOutput = cmd.getAppDispInfoStartupOutput().toDomain();
		Application application = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getApplication();
		// 9-1.詳細画面否認前の処理 Xử lý trước khi disapprove màn hình chi tiết 
		beforeRegisterRepo.exclusiveCheck(companyID, application.getAppID(), application.getVersion());
		// 9-2.詳細画面否認後の処理Xử lý sau khi disapprove màn hình chi tiết 
		processResult = detailAfterDeny.doDeny(companyID, application.getAppID(), application, appDispInfoStartupOutput, memo);
		if(!processResult.isProcessDone()) {
			return processResult;
		}
//		// IF文を参照
//		AppTypeSetting appTypeSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
//				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null);
//		boolean condition = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet() && appTypeSetting.isSendMailWhenApproval();
//		if(condition) {
//			processResult.setAutoSendMail(true);
//			// メニューの表示名を取得する
//			List<ListOfAppTypes> listOfAppTypes =  cmd.getListOfAppTypes().stream().map(x -> x.toDomain()).collect(Collectors.toList());
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
//			// 申請者本人にメール送信する(gửi mail cho người viết đơn)
//			MailResult mailResult = otherCommonAlgorithm.sendMailApplicantDeny(application, appName); 
//			processResult.setAutoSuccessMail(mailResult.getSuccessList());
//			processResult.setAutoFailMail(mailResult.getFailList());
//			processResult.setAutoFailServer(mailResult.getFailServerList());
//		}
		return processResult;
	}

}
