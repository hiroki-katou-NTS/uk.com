/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemPolicy;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUpdateDomainEvent;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrderRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalItemSaveCommandHandler.
 */
@Stateless
@Transactional
public class OptionalItemSaveCommandHandler extends CommandHandler<OptionalItemSaveCommand> {

	@Inject
	private OptionalItemPolicy optItemSv;

	@Inject
	private OptionalItemRepository optItemRepo;

	/** The repository. */
	@Inject
	private FormulaRepository formulaRepo;

	/** The order repo. */
	@Inject
	private FormulaDispOrderRepository orderRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OptionalItemSaveCommand> context) {
		// get company id
		String companyId = AppContexts.user().companyId();

		// Get command.
		OptionalItemSaveCommand command = context.getCommand();

		// Map to optionaItem domain
		OptionalItem dom = new OptionalItem(command);

		// Get optional item no
		int optionalItemNo = command.getOptionalItemNo().v();

		// Map to list domain Formula
		List<Formula> formulas = command.getFormulas().stream().map(item -> {
			return new Formula(item);
		}).collect(Collectors.toList());

		// Map to list domain FormulaDispOrder
		List<FormulaDispOrder> dispOrders = command.getFormulas().stream().map(item -> {
			return new FormulaDispOrder(item);
		}).collect(Collectors.toList());

		if (this.optItemSv.canRegister(dom, formulas)) {

			// update optional item.
			this.optItemRepo.update(dom);

			// Remove all existing formulas
			this.formulaRepo.remove(companyId, optionalItemNo);
			this.orderRepo.remove(companyId, optionalItemNo);

			// Insert new formulas
			this.formulaRepo.create(formulas);
			this.orderRepo.create(dispOrders);

			// Fire optional item update event
			// ドメインモデル「任意項目．属性」を更新した場合Event「任意項目の属性が更新された」を発行する
			OptionalItemUpdateDomainEvent event = new OptionalItemUpdateDomainEvent(dom.getOptionalItemNo(), dom.getOptionalItemAtr(), dom.getPerformanceAtr());
			event.toBePublished();
		}
	}

}
