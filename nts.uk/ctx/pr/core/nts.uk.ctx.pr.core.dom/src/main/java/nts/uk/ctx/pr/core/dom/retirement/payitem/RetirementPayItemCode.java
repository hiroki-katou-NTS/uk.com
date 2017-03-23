package nts.uk.ctx.pr.core.dom.retirement.payitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 退職金項目コード
 * @author Doan Duy Hung
 *
 */
@StringMaxLength(4)
public class RetirementPayItemCode extends StringPrimitiveValue<RetirementPayItemCode>{
	
	public RetirementPayItemCode(String value) {
		super(value);
		// TODO Auto-generated constructor stub
	}

}
