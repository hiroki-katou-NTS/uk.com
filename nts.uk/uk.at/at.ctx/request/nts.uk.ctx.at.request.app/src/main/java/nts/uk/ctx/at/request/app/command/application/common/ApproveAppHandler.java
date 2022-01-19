package nts.uk.ctx.at.request.app.command.application.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.applist.service.ListOfAppTypes;
import nts.uk.ctx.at.request.dom.application.common.service.application.ApproveAppProcedure;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApproveAppProcedureOutput;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.ApprovalProcessParam;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.AppDetailScreenInfo;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ApproveProcessResult;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class ApproveAppHandler extends CommandHandlerWithResult<AppDetailBehaviorCmd, ApproveProcessResult> {

	@Inject
	private DetailBeforeUpdate beforeRegisterRepo;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Inject
	private ApproveAppProcedure approveAppProcedure;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;

	@Override
	protected ApproveProcessResult handle(CommandHandlerContext<AppDetailBehaviorCmd> context) {
		String companyID = AppContexts.user().companyId();
		AppDetailBehaviorCmd cmd = context.getCommand(); 
		String memo = cmd.getMemo();
		AppDispInfoStartupOutput appDispInfoStartupOutput = cmd.getAppDispInfoStartupOutput().toDomain();
		AppDetailScreenInfo appDetailScreenInfo = appDispInfoStartupOutput.getAppDetailScreenInfo().get();
		Application application = appDetailScreenInfo.getApplication();
		ApprovalProcessParam approvalProcessParam = new ApprovalProcessParam(
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().isMailServerSet(),
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getApplicationSetting().getAppTypeSettings()
				.stream().filter(x -> x.getAppType()==application.getAppType()).findAny().orElse(null));
		return approve(companyID, application.getAppID(), application, approvalProcessParam, memo, Collections.emptyList(), false);
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
	public ApproveProcessResult approve(String companyID, String appID, Application application, ApprovalProcessParam approvalProcessParam, String memo,
			List<ListOfAppTypes> listOfAppTypes, boolean isFromCMM045) {
		ApproveProcessResult approveProcessResult = new ApproveProcessResult();
		//アルゴリズム「排他チェック」を実行する (thực hiện xử lý 「check version」)
        beforeRegisterRepo.exclusiveCheck(companyID, application.getAppID(), application.getVersion());
		
        List<Application> appLst = new ArrayList<>();
        appLst.add(application);
        List<AppHdsubRec> appHdsubRecLst = new ArrayList<>();
        if(application.getAppType()==ApplicationType.COMPLEMENT_LEAVE_APPLICATION && !isFromCMM045) {
        	Optional<AppHdsubRec> appHdsubRec = appHdsubRecRepository.findByAppId(appID);
			if(appHdsubRec.isPresent()) {
				appHdsubRecLst.add(appHdsubRec.get());
				if(appHdsubRec.get().getRecAppID().equals(appID)) {
					applicationRepository.findByID(appHdsubRec.get().getAbsenceLeaveAppID()).ifPresent(x -> appLst.add(x));
				} else {
					applicationRepository.findByID(appHdsubRec.get().getRecAppID()).ifPresent(x -> appLst.add(x));
				}
			}
        }
        ApplicationSetting applicationSetting = applicationSettingRepository.findByCompanyId(companyID).get();
        ApproveAppProcedureOutput approveAppProcedureOutput = approveAppProcedure.approveAppProcedure(
        		companyID, 
        		appLst, 
        		appHdsubRecLst, 
        		AppContexts.user().employeeId(), 
        		Optional.ofNullable(memo), 
        		applicationSetting.getAppTypeSettings(), 
        		false,
        		false);
        approveProcessResult.setProcessDone(true);
        if(isFromCMM045) {
        	return approveProcessResult;
        }
        // IF文を参照
 		AppTypeSetting appTypeSetting = approvalProcessParam.getAppTypeSetting();
 		boolean condition = approvalProcessParam.isMailServerSet() && appTypeSetting.isSendMailWhenApproval();
 		if(condition) {
 			approveProcessResult.setAutoSendMail(true);
 		}
 		approveProcessResult.setAutoSuccessMail(approveAppProcedureOutput.getSuccessList().stream().distinct().collect(Collectors.toList()));
 		approveProcessResult.setAutoFailMail(approveAppProcedureOutput.getFailList().stream().distinct().collect(Collectors.toList()));
 		approveProcessResult.setAutoFailServer(approveAppProcedureOutput.getFailServerList().stream().distinct().collect(Collectors.toList()));
 		return approveProcessResult;
	}

}
