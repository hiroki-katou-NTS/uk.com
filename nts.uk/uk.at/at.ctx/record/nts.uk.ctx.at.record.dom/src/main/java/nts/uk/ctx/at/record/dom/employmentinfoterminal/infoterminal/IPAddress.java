package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;

/**
 * @author ThanhNX
 * 
 *         IPアドレス
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(50)
@StringRegEx("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")
public class IPAddress extends StringPrimitiveValue<IPAddress> {

	private static final long serialVersionUID = 1L;

	public IPAddress(String rawValue) {
		super(rawValue);
	}

}
