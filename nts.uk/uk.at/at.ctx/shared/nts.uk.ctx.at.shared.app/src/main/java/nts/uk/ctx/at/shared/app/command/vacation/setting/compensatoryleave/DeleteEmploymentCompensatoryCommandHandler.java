package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteEmploymentCompensatoryCommandHandler.
 */
@Stateless
public class DeleteEmploymentCompensatoryCommandHandler extends CommandHandler<SaveEmploymentCompensatoryCommand> {

	@Inject
	CompensLeaveEmSetRepository compensLeaveEmSetRepository;
	
	@Override
	protected void handle(CommandHandlerContext<SaveEmploymentCompensatoryCommand> context) {
		SaveEmploymentCompensatoryCommand setting = context.getCommand();
		
		String companyId  = AppContexts.user().companyId();
		String employmentCode = setting.getEmploymentCode();
		compensLeaveEmSetRepository.delete(companyId, employmentCode);
	}

}
