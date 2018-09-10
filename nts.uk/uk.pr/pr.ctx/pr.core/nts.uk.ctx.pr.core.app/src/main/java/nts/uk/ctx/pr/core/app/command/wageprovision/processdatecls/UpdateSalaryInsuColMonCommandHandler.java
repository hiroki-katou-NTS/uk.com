package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SalaryInsuColMon;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SalaryInsuColMonRepository;

@Stateless
@Transactional
public class UpdateSalaryInsuColMonCommandHandler extends CommandHandler<SalaryInsuColMonCommand> {

	@Inject
	private SalaryInsuColMonRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SalaryInsuColMonCommand> context) {
		SalaryInsuColMonCommand updateCommand = context.getCommand();
		repository.update(new SalaryInsuColMon(
				updateCommand.getMonthCollected()));

	}
}
