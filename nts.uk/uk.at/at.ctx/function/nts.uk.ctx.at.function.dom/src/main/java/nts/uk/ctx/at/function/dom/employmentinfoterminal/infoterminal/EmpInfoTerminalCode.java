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
 *	就業情報端末コード	
 */

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
@ZeroPaddedCode
public class EmpInfoTerminalCode extends CodePrimitiveValue<EmpInfoTerminalCode>{
	
	private static final long serialVersionUID = 1L;

	public EmpInfoTerminalCode(String rawValue) {
		super(rawValue);
	}
}
