package nts.uk.ctx.at.request.app.command.application.common;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class DeleteAppEmploymentSetCommandHandler extends CommandHandler<AppEmploymentSetCommand>{

//	@Inject
//	AppEmploymentSettingRepository employmentSetting;

    // refactor 4
    @Inject
    private AppEmploymentSetRepository appEmploymentSetRepo;

	/**
	 * 削除処理
	 * @param context
	 */
	@Override
	protected void handle(CommandHandlerContext<AppEmploymentSetCommand> context) {
		
		AppEmploymentSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

        appEmploymentSetRepo.delete(companyId, command.getEmploymentCode());
	}

}
