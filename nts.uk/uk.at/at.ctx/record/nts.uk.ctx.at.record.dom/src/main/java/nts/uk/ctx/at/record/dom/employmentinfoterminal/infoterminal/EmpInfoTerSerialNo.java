package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 *
 *         就業情報端末シリアルNO
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(8)
public class EmpInfoTerSerialNo extends StringPrimitiveValue<EmpInfoTerSerialNo> {

	private static final long serialVersionUID = 1L;

	public EmpInfoTerSerialNo(String rawValue) {
		super(rawValue);
	}

}
