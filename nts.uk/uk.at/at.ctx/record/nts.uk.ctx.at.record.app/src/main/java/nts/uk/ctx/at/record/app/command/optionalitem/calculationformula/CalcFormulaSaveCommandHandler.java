/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optionalitem.calculationformula;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormula;

/**
 * The Class OptionalItemSaveCommandHandler.
 */
@Stateless
@Transactional
public class CalcFormulaSaveCommandHandler extends CommandHandler<CalcFormulaSaveCommand> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CalcFormulaSaveCommand> context) {
		CalcFormulaSaveCommand command = context.getCommand();
		List<OptionalItemFormula> list = command.getListCalcFormula().stream().map(item -> {
			return new OptionalItemFormula(item);
		}).collect(Collectors.toList());

		System.out.print(list);
		// TODO... wait for infra.
	}

}
