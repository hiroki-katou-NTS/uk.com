package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class RemoveOptionalAggrPeriodCommandHandler extends CommandHandler<RemoveOptionalAggrPeriodCommand>{

	@Inject
	private AnyAggrPeriodRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveOptionalAggrPeriodCommand> context) {
		String companyId = AppContexts.user().companyId();
		// get command
		RemoveOptionalAggrPeriodCommand command = context.getCommand();
		
		if(!this.repository.findOneByCompanyIdAndFrameCode(companyId, command.getAggrFrameCode()).isPresent()){
			throw new BusinessException(new RawErrorMessage("対象データがありません。"));
		}
		// delete process
		repository.deleteAnyAggrPeriod(companyId, command.getAggrFrameCode());
	
	}

}
