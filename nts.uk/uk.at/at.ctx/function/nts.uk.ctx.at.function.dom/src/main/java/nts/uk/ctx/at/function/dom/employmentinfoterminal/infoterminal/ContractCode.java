package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 
 * @author huylq
 *
 *	契約コード	
 */

@StringMaxLength(12)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class ContractCode extends CodePrimitiveValue<ContractCode>{
	/**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	public ContractCode (String rawValue){
		super(rawValue);
	}
}
