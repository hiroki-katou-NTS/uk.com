/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optionalitem.calculationformula;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.optionalitem.calculationformula.CalcFormulaDto;

/**
 * The Class CalcFormulaSaveCommand.
 */
@Getter
@Setter
public class CalcFormulaSaveCommand {

	/** The list calc formula. */
	private List<CalcFormulaDto> listCalcFormula;

}
