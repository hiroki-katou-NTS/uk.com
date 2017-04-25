/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.app.wageledger.command.dto.ItemSubjectDto;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLItemSubject;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AggregateItemSaveCommandHandler.
 */
@Stateless
public class AggregateItemSaveCommandHandler extends CommandHandler<AggregateItemSaveCommand> {

	/** The repository. */
	@Inject
	private WLAggregateItemRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<AggregateItemSaveCommand> context) {
		val companyCode = AppContexts.user().companyCode();
		val command = context.getCommand();

		// Validate required items.
		ItemSubjectDto subjectItem = command.getSubject();
		if (subjectItem.getCode() == null || subjectItem.getCode().equals("")) {
			throw new BusinessException("ER001");
		}
		if (command.getName() == null || command.getName().equals("")) {
			throw new BusinessException("ER001");
		}

		// Convert command to Domain.
		WLItemSubject subject = command.getSubject().toDomain(companyCode);
		subject.validate();
		// In case update.
		if (!command.isCreateMode()) {
			// Find aggregate item.
			WLAggregateItem aggregateItem = this.repository.findByCode(subject);
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
		if (this.repository.isExist(subject)) {
			throw new BusinessException("ER005");
		}

		// Convert to domain.
		val aggregateItem = command.toDomain(companyCode);
		this.repository.create(aggregateItem);
		return;
	}

}
