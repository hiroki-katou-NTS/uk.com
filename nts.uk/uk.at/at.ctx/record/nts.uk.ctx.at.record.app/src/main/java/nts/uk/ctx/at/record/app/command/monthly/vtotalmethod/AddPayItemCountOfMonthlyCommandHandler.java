package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.PayItemCountOfMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddPayItemCountOfMonthlyCommandHandler.
 * @author HoangNDH
 */
@Stateless
public class AddPayItemCountOfMonthlyCommandHandler extends CommandHandler<AddPayItemCountOfMonthlyCommand> {
	
	/** The repository. */
	@Inject
	PayItemCountOfMonthlyRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddPayItemCountOfMonthlyCommand> context) {
		AddPayItemCountOfMonthlyCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		PayItemCountOfMonthly setting = command.toDomain(companyId);
		
		// Remove old records and add new one
		repository.persistAndUpdate(setting);
	}
	
}
