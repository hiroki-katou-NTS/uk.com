package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateAffWorkplaceHistoryCommandHandler extends CommandHandler<UpdateAffWorkplaceHistoryCommand>
	implements PeregUpdateCommandHandler<UpdateAffWorkplaceHistoryCommand>{
	
	@Inject
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00010";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateAffWorkplaceHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateAffWorkplaceHistoryCommand> context) {
		val command = context.getCommand();
		
		AffWorkplaceHistory domain =  AffWorkplaceHistory.createFromJavaType(command.getWorkplaceId(),command.getStartDate(), command.getEndDate(), command.getEmployeeId());
		
		affWorkplaceHistoryRepository.updateAffWorkplaceHistory(domain);
	}
	
}
