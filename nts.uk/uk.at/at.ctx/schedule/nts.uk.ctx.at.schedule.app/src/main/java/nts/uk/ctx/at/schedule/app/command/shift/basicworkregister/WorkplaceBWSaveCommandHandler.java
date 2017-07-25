package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;

@Stateless
public class WorkplaceBWSaveCommandHandler extends CommandHandler<WorkplaceBWSaveCommand> {

	@Inject
	private WorkplaceBasicWorkRepository repository;

	@Override
	protected void handle(CommandHandlerContext<WorkplaceBWSaveCommand> context) {
		// get user login
//		LoginUserContext loginUserContext = AppContexts.user();

		// get company id user login
//		String companyId = loginUserContext.companyId();

		// Get Command
		WorkplaceBWSaveCommand command = context.getCommand();
		
		// Get Workplace Id
		String workplaceId = command.getWorkplaceBasicWork().getWorkplaceId();
		
		// Get WorkType code
//		String worktypeCode = command.getWorkplaceBasicWork().getBasicWorkSetting().get(0).getWorkTypeCode();
		
		// Get workdayDivision
		Integer workdayDivision = command.getWorkplaceBasicWork().getBasicWorkSetting().get(0).getWorkDayDivision();
		
		Optional<WorkplaceBasicWork> optional = this.repository.find(workplaceId, workdayDivision);
		
		// Convert to Domain
		WorkplaceBasicWork workplaceBasicWork = command.toDomain();
		
		// Validate 
		workplaceBasicWork.validate();
		
		// Check if exist
		if(optional.isPresent()) {
			this.repository.update(workplaceBasicWork);
		} else {
			this.repository.insert(workplaceBasicWork);
		}
	}
}
