package nts.uk.ctx.pr.core.dom.retirement.payitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 項目略名英語
 * @author Doan Duy Hung
 *
 */

@StringMaxLength(12)
public class RetirementPayItemEnglishName extends StringPrimitiveValue<RetirementPayItemEnglishName>{
	public RetirementPayItemEnglishName(String value) {
		super(value);
		// TODO Auto-generated constructor stub
	}
}
