/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.estcomparison;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparison;
import nts.uk.ctx.at.schedule.dom.shift.estimate.estcomparison.EstimateComparisonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EstimateComparisonSaveCommandHandler.
 */
@Stateless
public class EstimateComparisonSaveCommandHandler extends CommandHandler<EstimateComparisonSaveCommand>{
	
	/** The repository. */
	@Inject
	private EstimateComparisonRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EstimateComparisonSaveCommand> context) {
		// Get company id.
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		EstimateComparisonSaveCommand command = context.getCommand();
		
		// To Domain
		EstimateComparison estComparison = command.toDomain(companyId);
		
		// Find By Company Id
		Optional<EstimateComparison> opt = this.repository.findByCompanyId(companyId);
		
		// Check isPresent
		if (opt.isPresent()) {
			this.repository.remove(companyId);
		}
		
		// Save Estimate Comparison
		this.repository.add(estComparison);
	}

}
