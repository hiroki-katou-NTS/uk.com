package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author thanhpv
 * メール件名
 */

@StringMaxLength(100)
public class Subject extends StringPrimitiveValue<Subject> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Subject(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
