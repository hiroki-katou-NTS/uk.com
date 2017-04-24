package nts.uk.ctx.pr.core.app.command.rule.employment.processing.yearmonth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class Qmm005bCommandHandler extends CommandHandler<Qmm005bCommand> {

	@Inject
	private PaydayRepository paydayRepo;

	@Override
	protected void handle(CommandHandlerContext<Qmm005bCommand> context) {
		String companyCode = AppContexts.user().companyCode();

		Qmm005bCommand commands = context.getCommand();

		for (PayDayUpdateCommand command : commands.getPayDays()) {
			paydayRepo.update1(command.toDomain(companyCode));
		}
	}
}
