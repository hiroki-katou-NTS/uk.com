package nts.uk.ctx.bs.employee.app.command.workplace.affiliate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistoryRepository;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;

@Stateless
public class AddAffWorkplaceHistoryCommandHandler extends CommandHandler<AddAffWorkplaceHistoryCommand>
	implements PeregAddCommandHandler<AddAffWorkplaceHistoryCommand>{
	
	@Inject
	private AffWorkplaceHistoryRepository affWorkplaceHistoryRepository;
	
	@Override
	public String targetCategoryId() {
		return "CS00010";
	}

	@Override
	public Class<?> commandClass() {
		return AddAffWorkplaceHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<AddAffWorkplaceHistoryCommand> context) {
		val command = context.getCommand();
		
		String newId = IdentifierUtil.randomUniqueId();
		
		AffWorkplaceHistory domain =  AffWorkplaceHistory.createFromJavaType(newId,command.getStartDate(), command.getEndDate(), command.getEmployeeId());
		
		affWorkplaceHistoryRepository.addAffWorkplaceHistory(domain);
	}
	
}
