package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddAffWorkplaceHistoryCommandHandler extends CommandHandlerWithResult<AddAffWorkplaceHistoryCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddAffWorkplaceHistoryCommand>{
	
	@Inject
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00010";
	}

	@Override
	public Class<?> commandClass() {
		return AddAffWorkplaceHistoryCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddAffWorkplaceHistoryCommand> context) {
		val command = context.getCommand();
		
		String newId = IdentifierUtil.randomUniqueId();
		
		AffWorkplaceHistory domain =  AffWorkplaceHistory.createFromJavaType(newId,command.getStartDate(), command.getEndDate(), command.getEmployeeId());
		
		affWorkplaceHistoryRepository.addAffWorkplaceHistory(domain);
		return new PeregAddCommandResult(newId);
	}
	
}
