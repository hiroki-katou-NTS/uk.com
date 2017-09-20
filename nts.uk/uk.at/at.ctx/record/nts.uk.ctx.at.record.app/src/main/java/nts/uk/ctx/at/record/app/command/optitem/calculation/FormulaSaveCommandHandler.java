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
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OptionalItemSaveCommandHandler.
 */
@Stateless
@Transactional
public class FormulaSaveCommandHandler extends CommandHandler<FormulaSaveCommand> {

	@Inject
	private FormulaRepository repository;

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
		List<Formula> list = command.getListCalcFormula().stream().map(item -> {
			return new Formula(item);
		}).collect(Collectors.toList());

		// TODO
		this.repository.remove(AppContexts.user().companyCode(), "itemNo");
		this.repository.create(list);
		;
	}

}
