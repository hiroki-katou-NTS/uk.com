package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author thanhpv
 *
 */
@StringMaxLength(256)
public class MailAddress extends StringPrimitiveValue<MailAddress>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MailAddress(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
