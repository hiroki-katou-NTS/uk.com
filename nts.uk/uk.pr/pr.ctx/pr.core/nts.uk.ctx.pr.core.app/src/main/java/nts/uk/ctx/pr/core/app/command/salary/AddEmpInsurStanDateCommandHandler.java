package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.EmpInsurStanDate;
import nts.uk.ctx.pr.core.dom.salary.EmpInsurStanDateRepository;

@Stateless
@Transactional
public class AddEmpInsurStanDateCommandHandler extends CommandHandler<EmpInsurStanDateCommand> {

	@Inject
	private EmpInsurStanDateRepository repository;

	@Override
	protected void handle(CommandHandlerContext<EmpInsurStanDateCommand> context) {
		EmpInsurStanDateCommand addCommand = context.getCommand();
		repository.add(new EmpInsurStanDate(addCommand.getCid(), addCommand.getProcessCateNo(),
				addCommand.getRefeDate(), addCommand.getBaseMonth()));

	}
}
