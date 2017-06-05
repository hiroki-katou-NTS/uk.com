package nts.uk.ctx.at.shared.dom.worktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(3)
public class WorkTimeSymbol extends StringPrimitiveValue<WorkTimeSymbol>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4133286692955063587L;

	public WorkTimeSymbol(String rawValue) {
		super(rawValue);
	}

}
