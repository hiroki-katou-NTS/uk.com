/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AggregateItemSaveCommandHandler.
 */
@Stateless
public class AggregateItemSaveCommandHandler extends CommandHandler<AggregateItemSaveCommand>{
	
	/** The repository. */
	@Inject
	private WLAggregateItemRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AggregateItemSaveCommand> context) {
		val companyCode = AppContexts.user().companyCode();
		val command = context.getCommand();
		
		// In case update.
		if (!command.isCreateMode) {
			// Find aggregate item.
			WLAggregateItem aggregateItem = this.repository.find(new WLAggregateItemCode(command.code),
					new CompanyCode(companyCode));
			if (aggregateItem == null) {
				throw new BusinessException("ER026");
			}
			
			// Convert to domain.
			aggregateItem = command.toDomain(companyCode);
			// Update.
			this.repository.update(aggregateItem);
			return;
		}
		
		// In case create.
		// Check duplicate code.
		if (this.repository.isExist(new WLAggregateItemCode(command.code))) {
			throw new BusinessException("ER011");
		}
		
		// Convert to domain.
		val aggregateItem = command.toDomain(companyCode);
		this.repository.create(aggregateItem);
		return;
	}
	
}
