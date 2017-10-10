/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrder;
import nts.uk.ctx.at.record.dom.optitem.calculation.disporder.FormulaDispOrderRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class FormulaSaveCommandHandler.
 */
@Stateless
@Transactional
public class FormulaSaveCommandHandler extends CommandHandler<FormulaSaveCommand> {

	/** The repository. */
	@Inject
	private FormulaRepository repository;

	/** The order repo. */
	@Inject
	private FormulaDispOrderRepository orderRepo;

	
	/** The Constant FIRST_ITEM. */
	public static final int FIRST_ITEM = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<FormulaSaveCommand> context) {
		FormulaSaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		List<Formula> formulas = command.getCalcFormulas().stream().map(item -> {
			return new Formula(item);
		}).collect(Collectors.toList());

		List<FormulaDispOrder> dispOrders = command.getCalcFormulas().stream().map(item -> {
			return new FormulaDispOrder(item);
		}).collect(Collectors.toList());

		String optionalItemNo = command.getOptItemNo();
		this.repository.remove(companyId, optionalItemNo);
		this.orderRepo.remove(companyId, optionalItemNo);

		this.repository.create(formulas);
		this.orderRepo.create(dispOrders);
	}

}
