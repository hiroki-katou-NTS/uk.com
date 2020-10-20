package nts.uk.ctx.at.request.app.command.application.common;



import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;


import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
@Transactional
public class UpdateAppEmploymentSetCommandHandler extends CommandHandler<AppEmploymentSetCommand>{
//	@Inject
//	AppEmploymentSettingRepository employmentSetting;

	// refactor 4
	@Inject
	AppEmploymentSetRepository appEmploymentSetRepo;

	/**
	 * 登録処理
	 * @param context
	 */
	@Override
	protected void handle(CommandHandlerContext<AppEmploymentSetCommand> context) {
		// 会社ID
		String companyId = AppContexts.user().companyId();
		AppEmploymentSetCommand appEmploymentSetting = context.getCommand();
		AppEmploymentSet updateData = appEmploymentSetting.toNewDomain(companyId);
		appEmploymentSetRepo.update(updateData);
	}

}
