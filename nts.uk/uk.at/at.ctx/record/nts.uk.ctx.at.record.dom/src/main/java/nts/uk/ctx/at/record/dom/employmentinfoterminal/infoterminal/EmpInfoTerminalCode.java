package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * @author ThanhNX
 *
 *         就業情報端末コード
 */

@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
@ZeroPaddedCode
public class EmpInfoTerminalCode extends CodePrimitiveValue<EmpInfoTerminalCode> {

	private static final long serialVersionUID = 1L;

	public EmpInfoTerminalCode(String rawValue) {
		super(rawValue);
	}


}