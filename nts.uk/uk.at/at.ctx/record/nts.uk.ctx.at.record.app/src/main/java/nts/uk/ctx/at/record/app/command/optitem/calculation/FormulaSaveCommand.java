/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem.calculation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class CalcFormulaSaveCommand.
 */
@Getter
@Setter
public class FormulaSaveCommand {

	/** The opt item no. */
	private String optItemNo;

	/** The list calc formula. */
	private List<FormulaDto> calcFormulas;

}
