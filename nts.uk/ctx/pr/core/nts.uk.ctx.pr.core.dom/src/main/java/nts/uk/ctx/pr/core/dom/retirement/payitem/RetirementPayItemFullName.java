package nts.uk.ctx.pr.core.dom.retirement.payitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 項目略名多言語
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(12)
public class RetirementPayItemFullName extends StringPrimitiveValue<RetirementPayItemFullName>{
	public RetirementPayItemFullName(String value) {
		super(value);
	}
}
