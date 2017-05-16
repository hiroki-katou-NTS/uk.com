package nts.uk.ctx.pr.core.app.command.rule.employment.allot;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingHeaderRespository;
import nts.uk.ctx.pr.core.dom.rule.employment.layout.allot.EmployeeAllotSettingRepository;

@Stateless
@Transactional
public class DeleteEmployeeAllotHeaderCommandHandler extends CommandHandler<DeleteEmployeeAllotHeaderCommand> {
	@Inject
	EmployeeAllotSettingRepository empRepo;

	@Inject
	private EmployeeAllotSettingHeaderRespository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteEmployeeAllotHeaderCommand> context) {
		DeleteEmployeeAllotHeaderCommand command = context.getCommand();

		repo.delete(command.toDomain());

		for (EmployeeCommand emp : command.getEmployees()) {
			empRepo.delete(emp.toDomain(command.getHistoryId()));
		}
	}
}