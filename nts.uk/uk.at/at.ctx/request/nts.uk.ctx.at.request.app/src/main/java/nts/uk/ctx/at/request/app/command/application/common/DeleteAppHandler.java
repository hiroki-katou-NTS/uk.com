package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ApplicationTypeDisplay;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDelete;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.MailResult;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.HolidayService_Old;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class DeleteAppHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ProcessResult> {
	
	@Inject
	private AfterProcessDelete afterProcessDelete;
	
	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject 
	private HolidayService_Old holidayService;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;

	@Override
	protected ProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		ProcessResult processResult = new ProcessResult();
		AppDetailBehaviorCmd cmd = context.getCommand();
		Application application = cmd.getAppDispInfoStartupOutput().getAppDetailScreenInfo().getApplication().toDomain();
		AppDispInfoStartupOutput appDispInfoStartupOutput = cmd.getAppDispInfoStartupOutput().toDomain();
		String appID = application.getAppID();
		int version = application.getVersion();
		
		beforeRegisterRepo.exclusiveCheck(companyID, appID, version);
		// 共通アルゴリズム「詳細画面削除後の処理」を実行する(thực hiện xử lý 「詳細画面削除後の処理」)
		List<String> destinationLst = afterProcessDelete.screenAfterDelete(
				appID, 
				application, 
				appDispInfoStartupOutput);
		processResult.setProcessDone(true);
//		// IF文を参照
//		AppTypeSetting appTypeSetting = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
//				.stream().filter(x -> x.getAppType() == application.getAppType()).findAny().get();
//		boolean condition = appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet() && 
//				appTypeSetting.isSendMailWhenRegister();
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
//			// 送信先リストに項目がいるかチェックする(kiểm tra danh sách người xác nhận có mục nào hay không)
//			if(!CollectionUtil.isEmpty(destinationLst)){
//				// 送信先リストにメールを送信する(gửi mail cho danh sách người xác nhận)
//				MailResult mailResult = otherCommonAlgorithm.sendMailApproverDelete(destinationLst, application, appName);
//				processResult.setAutoSuccessMail(mailResult.getSuccessList());
//				processResult.setAutoFailMail(mailResult.getFailList());
//				processResult.setAutoFailServer(mailResult.getFailServerList());
//			}
//		}
		
		// アルゴリズム「11.休出申請（振休変更）削除」を実行する
		if(application.getAppType()==ApplicationType.HOLIDAY_WORK_APPLICATION){
			holidayService.delHdWorkByAbsLeaveChange(appID);
		}
		processResult.setAppID(appID);
		return processResult;
	}
}
