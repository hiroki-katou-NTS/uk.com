package nts.uk.ctx.at.request.app.command.application.common;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.shr.com.context.AppContexts;

public class UpdateApplicationSettingCommandHandler extends CommandHandler<ApplicationSettingCommand>{
	@Inject
	private ApplicationSettingRepository appRep;
	/**
	 * update application setting
	 * @author yennth
	 */
	@Override
	protected void handle(CommandHandlerContext<ApplicationSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		ApplicationSettingCommand data = context.getCommand();
		ApplicationSetting appli = ApplicationSetting.createFromJavaType(companyId, 
				data.getAppActLockFlg(), data.getAppEndWorkFlg(), 
				data.getAppActConfirmFlg(), data.getAppOvertimeNightFlg(), 
				data.getAppActMonthConfirmFlg(), data.getRequireAppReasonFlg(), 
				data.getDisplayPrePostFlg(), data.getDisplaySearchTimeFlg(), 
				data.getManualSendMailAtr(), data.getBaseDateFlg(), 
				data.getAdvanceExcessMessDispAtr(), data.getHwAdvanceDispAtr(), 
				data.getHwActualDispAtr(), data.getActualExcessMessDispAtr(), 
				data.getOtAdvanceDispAtr(), data.getOtActualDispAtr(), 
				data.getWarningDateDispAtr(), data.getAppReasonDispAtr(), 
				data.getAppContentChangeFlg(), data.getScheReflectFlg(), 
				data.getPriorityTimeReflectFlg(), data.getAttendentTimeReflectFlg());
		appRep.updateSingle(appli);
	}
}
