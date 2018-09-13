package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Hoidd
 *
 */
@AllArgsConstructor
public enum CheckOperatorTypeImport {
	
	SINGLE_VALUE(0, "single"),
	RANGE_VALUE(1, "range");	

	public final int value;

	public final String nameId;
}
