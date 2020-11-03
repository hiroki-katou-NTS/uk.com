package nts.uk.ctx.at.request.app.command.application.workchange;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.request.app.command.application.common.CreateApplicationCommand;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IWorkChangeUpdateService;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
@Transactional
public class UpdateAppWorkChangeCommandHandler extends CommandHandlerWithResult<AddAppWorkChangeCommandPC, ProcessResult> {
	
	@Inject
	private IWorkChangeUpdateService updateService;

	@Override
	protected ProcessResult handle(CommandHandlerContext<AddAppWorkChangeCommandPC> context) {
		String companyID = AppContexts.user().companyId();
		CreateApplicationCommand applicationCommand = context.getCommand().getApplication();
		AppWorkChangeCommand appWorkChangeCommand = context.getCommand().getWorkChange();
		Application application = context.getCommand().getAppDispInfoStartupOutput().getAppDetailScreenInfo().getApplication().toDomain();
		application.setOpAppReason(Strings.isEmpty(applicationCommand.getOpAppReason()) ? Optional.empty() : Optional.of(new AppReason(applicationCommand.getOpAppReason())));
		application.setOpAppStandardReasonCD(applicationCommand.getOpAppStandardReasonCD() == null 
				? Optional.empty() 
				: Optional.of(new AppStandardReasonCode(applicationCommand.getOpAppStandardReasonCD())));
		AppWorkChange appWorkChange = new AppWorkChange(application);
		appWorkChange.setStraightGo(NotUseAtr.USE);
		appWorkChange.setStraightBack(NotUseAtr.USE);
		appWorkChange.setOpWorkTypeCD(Optional.of(new WorkTypeCode(appWorkChangeCommand.getWorkTypeCD())));
		appWorkChange.setOpWorkTimeCD(Optional.of(new WorkTimeCode(appWorkChangeCommand.getWorkTimeCD())));
		List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = Arrays.asList(
				new TimeZoneWithWorkNo(1, appWorkChangeCommand.getStartTime1(), appWorkChangeCommand.getEndTime1()),
				new TimeZoneWithWorkNo(2, 900, 1000));
		appWorkChange.setTimeZoneWithWorkNoLst(timeZoneWithWorkNoLst);
		return updateService.updateWorkChange(companyID, application, appWorkChange, context.getCommand().getAppDispInfoStartupOutput().toDomain());
	}

}
