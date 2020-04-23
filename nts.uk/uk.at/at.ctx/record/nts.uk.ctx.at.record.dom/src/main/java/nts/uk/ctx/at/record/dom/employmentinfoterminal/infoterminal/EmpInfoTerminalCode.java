package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 *
 *         就業情報端末コード
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
public class EmpInfoTerminalCode extends IntegerPrimitiveValue<EmpInfoTerminalCode> {

	private static final long serialVersionUID = 1L;

	public EmpInfoTerminalCode(Integer rawValue) {
		super(rawValue);
	}
}