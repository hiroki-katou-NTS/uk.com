package nts.uk.ctx.bs.employee.dom.workplace.differinfor;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(12)
@StringCharType(CharType.ALPHA_NUMERIC)
@ZeroPaddedCode
public class ContractCd extends CodePrimitiveValue<ContractCd>{
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	public ContractCd (String rawValue){
		super(rawValue);
	}
}
