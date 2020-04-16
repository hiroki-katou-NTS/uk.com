package nts.uk.ctx.at.request.app.command.application.common;



import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateAppEmploymentSetCommandHandler extends CommandHandler<AppEmploymentSetCommand>{
	@Inject
	AppEmploymentSettingRepository employmentSetting;
	@Override
	protected void handle(CommandHandlerContext<AppEmploymentSetCommand> context) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		AppEmploymentSetCommand appEmploymentSetting = context.getCommand();
		appEmploymentSetting.setCompanyID(companyId);
		AppEmploymentSetting insertData = appEmploymentSetting.toDomain();
		employmentSetting.update(insertData);
	}

}
