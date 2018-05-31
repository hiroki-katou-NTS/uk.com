package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateAppOvertimeSettingCommandHandler extends CommandHandler<AppOvertimeSettingCommand> {
	@Inject
	private AppOvertimeSettingRepository appOverRep;

	@Override
	protected void handle(CommandHandlerContext<AppOvertimeSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		AppOvertimeSettingCommand data = context.getCommand();
		Optional<AppOvertimeSetting> appOver = appOverRep.getAppOver();
		AppOvertimeSetting appOvertime = AppOvertimeSetting.createFromJavaType(companyId, 
				data.getFlexExcessUseSetAtr(), data.getPreTypeSiftReflectFlg(), data.getPreOvertimeReflectFlg(), 
				data.getPostTypesiftReflectFlg(), data.getPostBreakReflectFlg(), 
				data.getPostWorktimeReflectFlg(), data.getCalendarDispAtr(), data.getEarlyOverTimeUseAtr(), 
				data.getInstructExcessOtAtr(), data.getPriorityStampSetAtr(), data.getUnitAssignmentOvertime(), 
				data.getNormalOvertimeUseAtr(), data.getAttendanceId(), data.getUseOt(), data.getRestAtr(), 1);
		if(appOver.isPresent()){
			appOverRep.update(appOvertime);
			return;
		}
		appOverRep.insert(appOvertime);
	};
	
}
