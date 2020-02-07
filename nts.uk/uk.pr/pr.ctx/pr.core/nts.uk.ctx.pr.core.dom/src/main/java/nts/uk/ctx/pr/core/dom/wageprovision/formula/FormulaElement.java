package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 
 * 計算式要素
 *
 */

@StringMaxLength(20)
public class FormulaElement extends StringPrimitiveValue<FormulaElement> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public FormulaElement(String rawValue) {
		super(rawValue);
	}

}
