package nts.uk.ctx.bs.employee.app.command.holidaysetting.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;
import nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceMonthDaySettingRemoveCommandHandler.
 */
@Stateless
public class WorkplaceMonthDaySettingRemoveCommandHandler extends CommandHandler<WorkplaceMonthDaySettingRemoveCommand>{
	
	/** The repository. */
	@Inject
	private WorkplaceMonthDaySettingRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkplaceMonthDaySettingRemoveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get command
		WorkplaceMonthDaySettingRemoveCommand command = context.getCommand();
		
		// Remove
		this.repository.remove(new CompanyId(companyId), command.getWorkplaceId(), new Year(command.getYear()));
	}

}
