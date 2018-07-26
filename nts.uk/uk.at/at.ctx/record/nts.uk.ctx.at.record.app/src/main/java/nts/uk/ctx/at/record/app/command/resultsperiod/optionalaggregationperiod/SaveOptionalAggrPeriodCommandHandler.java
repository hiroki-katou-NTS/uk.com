package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriod;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class SaveOptionalAggrPeriodCommandHandler extends CommandHandler<SaveOptionalAggrPeriodCommand>{

	@Inject
	private OptionalAggrPeriodRepository repository; 
	
	@Override
	protected void handle(CommandHandlerContext<SaveOptionalAggrPeriodCommand> context) {
		SaveOptionalAggrPeriodCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		OptionalAggrPeriod optionalAggrPeriod = command.toDomain(companyId);
		optionalAggrPeriod.validate();
		Optional<OptionalAggrPeriod> optional = this.repository.findByCid(companyId);
		if(optional.isPresent()){
			this.repository.updateOptionalAggrPeriod(optionalAggrPeriod);
		}else{
			this.repository.addOptionalAggrPeriod(optionalAggrPeriod);
		}
	}

}
