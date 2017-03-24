package nts.uk.ctx.pr.core.dom.retirement.payitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 退職金項目名称
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(20)
public class RetirementPayItemName extends StringPrimitiveValue<RetirementPayItemName>{
	public RetirementPayItemName(String value) {
		super(value);
	}
}
