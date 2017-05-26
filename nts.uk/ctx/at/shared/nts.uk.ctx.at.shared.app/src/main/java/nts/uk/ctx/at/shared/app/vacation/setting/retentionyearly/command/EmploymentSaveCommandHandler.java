package nts.uk.ctx.at.shared.app.vacation.setting.retentionyearly.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class EmploymentSaveCommandHandler extends CommandHandler<EmploymentSaveCommand>{

	@Inject
	private EmploymentSettingRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<EmploymentSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyCode = loginUserContext.companyCode();

		// Get Command
		EmploymentSaveCommand command = context.getCommand();
		
		EmploymentSetting employmentSetting = command.toDomain(companyCode);
		
		// Validate
		employmentSetting.validate();
		
		// Update
		this.repository.update(employmentSetting);
	}
	
	
}
