package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * @author phongtq
 */
@Stateless
public class SaveOptionalAggrPeriodCommandHandler extends CommandHandler<SaveOptionalAggrPeriodCommand> {

	@Inject
	private AnyAggrPeriodRepository repository;

	@Override
	protected void handle(CommandHandlerContext<SaveOptionalAggrPeriodCommand> context) {
		SaveOptionalAggrPeriodCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		AnyAggrPeriod anyAggrPeriod = AnyAggrPeriod.createFromMemento(companyId, command);
		anyAggrPeriod.validate();
		// TODO nws-minhnb need check
//		Optional<AnyAggrPeriod> optional = this.repository.findByCompanyId(companyId);
//		if (optional.isPresent()) {
//			this.repository.updateAnyAggrPeriod(anyAggrPeriod);
//		} else {
//			this.repository.addAnyAggrPeriod(anyAggrPeriod);
//		}
	}

}
