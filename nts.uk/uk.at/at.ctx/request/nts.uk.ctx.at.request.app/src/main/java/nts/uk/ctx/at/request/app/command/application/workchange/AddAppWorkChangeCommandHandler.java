package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.IFactoryApplication;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeRegisterService;
import nts.uk.ctx.at.request.dom.application.workchange.output.AppWorkChangeDispInfo;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting.DisplayAtr;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddAppWorkChangeCommandHandler extends CommandHandlerWithResult<AddAppWorkChangeCommand, ProcessResult> {
	
	@Inject
	private IWorkChangeRegisterService workChangeRegisterService;
	
	@Inject
	private IFactoryApplication iFactoryApplication;
	
	@Override
	protected ProcessResult handle(CommandHandlerContext<AddAppWorkChangeCommand> context) {
		AddAppWorkChangeCommand command = context.getCommand();
		
		AppWorkChangeDispInfo appWorkChangeDispInfo = command.getAppWorkChangeDispInfoCmd().toDomain();
		// Application command
		CreateApplicationCommand appCommand = command.getApplication();
		// Work change command
		AppWorkChangeCommand workChangeCommand = command.getWorkChange();
		// 会社ID
		String companyId = AppContexts.user().companyId();
		// 申請ID
		String appID = IdentifierUtil.randomUniqueId();
		// 入力者 = 申請者
		// 申請者
		String applicantSID = Strings.isNotBlank(appCommand.getApplicantSID()) ? appCommand.getApplicantSID() : AppContexts.user().employeeId();
		
		AppTypeSetting appTypeSetting = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
				.getRequestSetting().getApplicationSetting().getListAppTypeSetting().stream()
				.filter(x -> x.getAppType() == ApplicationType_Old.WORK_CHANGE_APPLICATION).findFirst().get();
		
		String appReason = Strings.EMPTY;	
		String typicalReason = Strings.EMPTY;
		String displayReason = Strings.EMPTY;
		if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY){
			typicalReason += appCommand.getAppReasonID();
		}
		if(appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){
			if(Strings.isNotBlank(typicalReason)){
				displayReason += System.lineSeparator();
			}
			displayReason += appCommand.getApplicationReason();
		}
		ApplicationSetting applicationSetting = appWorkChangeDispInfo.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput()
				.getRequestSetting().getApplicationSetting();
		if(appTypeSetting.getDisplayFixedReason() == DisplayAtr.DISPLAY
			|| appTypeSetting.getDisplayAppReason() == DisplayAtr.DISPLAY){
			if (applicationSetting.getAppLimitSetting().getRequiredAppReason()
					&& Strings.isBlank(typicalReason+displayReason)) {
				throw new BusinessException("Msg_115");
			}
		}
		appReason = typicalReason + displayReason;
		
		// 申請
		Application_New app = iFactoryApplication.buildApplication(appID, appCommand.getStartDate(), appCommand.getPrePostAtr(), appReason, 
				appReason, ApplicationType_Old.WORK_CHANGE_APPLICATION, appCommand.getStartDate(), appCommand.getEndDate(), applicantSID);
					
		// 勤務変更申請
		AppWorkChange_Old workChangeDomain = AppWorkChange_Old.createFromJavaType(
				companyId, 
				appID,
				workChangeCommand.getWorkTypeCd(), 
				workChangeCommand.getWorkTimeCd(),
				workChangeCommand.getExcludeHolidayAtr(), 
				workChangeCommand.getWorkChangeAtr(),
				workChangeCommand.getGoWorkAtr1(), 
				workChangeCommand.getBackHomeAtr1(),
				workChangeCommand.getBreakTimeStart1(), 
				workChangeCommand.getBreakTimeEnd1(),
				workChangeCommand.getWorkTimeStart1(), 
				workChangeCommand.getWorkTimeEnd1(),
				workChangeCommand.getWorkTimeStart2(), 
				workChangeCommand.getWorkTimeEnd2(),
				workChangeCommand.getGoWorkAtr2(), 
				workChangeCommand.getBackHomeAtr2());
		
		List<GeneralDate> lstDateHd = command.getHolidayDateLst().stream()
				.map(x -> GeneralDate.fromString(x, "yyyy/MM/dd")).collect(Collectors.toList());
		
		//ドメインモデル「勤務変更申請設定」の新規登録をする
        return workChangeRegisterService.registerData(workChangeDomain, app, lstDateHd);
	}
}
