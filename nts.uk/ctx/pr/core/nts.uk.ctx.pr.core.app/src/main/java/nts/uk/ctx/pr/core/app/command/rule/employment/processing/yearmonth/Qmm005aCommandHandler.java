package nts.uk.ctx.pr.core.app.command.rule.employment.processing.yearmonth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.PaydayProcessingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class Qmm005aCommandHandler extends CommandHandler<Qmm005aCommand> {

	@Inject
	private PaydayProcessingRepository paydayProcessingRepo;

	@Override
	protected void handle(CommandHandlerContext<Qmm005aCommand> context) {
		Qmm005aCommand command = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		try {
			paydayProcessingRepo.update2(command.toDomain(companyCode));
		} catch (Exception ex) {
			throw ex;
		}
	}

}
