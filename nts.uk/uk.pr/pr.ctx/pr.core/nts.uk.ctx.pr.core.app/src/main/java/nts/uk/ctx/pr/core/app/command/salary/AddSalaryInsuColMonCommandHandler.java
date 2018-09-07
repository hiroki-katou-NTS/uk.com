package nts.uk.ctx.pr.core.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.salary.SalaryInsuColMon;
import nts.uk.ctx.pr.core.dom.salary.SalaryInsuColMonRepository;

@Stateless
@Transactional
public class AddSalaryInsuColMonCommandHandler extends CommandHandler<SalaryInsuColMonCommand> {

	@Inject
	private SalaryInsuColMonRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SalaryInsuColMonCommand> context) {
		SalaryInsuColMonCommand addCommand = context.getCommand();
		repository.add(new SalaryInsuColMon(addCommand.getProcessCateNo(), addCommand.getCid(),
				addCommand.getMonthCollected()));

	}
}
