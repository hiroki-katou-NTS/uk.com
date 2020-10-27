package nts.uk.ctx.sys.auth.dom.personal.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 連絡先のアドレス
 */
@StringMaxLength(20)
public class OtherContactAddress extends StringPrimitiveValue<OtherContactAddress> {
	/**
	*
	*/
	private static final long serialVersionUID = 1L;

	public OtherContactAddress(String rawValue) {
		super(rawValue);
	}
}
