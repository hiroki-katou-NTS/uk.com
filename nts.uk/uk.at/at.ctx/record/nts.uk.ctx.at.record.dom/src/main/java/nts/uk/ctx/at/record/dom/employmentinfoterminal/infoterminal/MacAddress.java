package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author ThanhNX
 * 
 *         MACアドレス
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(12)
public class MacAddress extends StringPrimitiveValue<MacAddress> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MacAddress(String rawValue) {
		super(rawValue);
	}

}
