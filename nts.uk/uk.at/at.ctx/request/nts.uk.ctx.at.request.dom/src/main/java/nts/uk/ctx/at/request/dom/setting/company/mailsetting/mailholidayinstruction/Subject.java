package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * メール件名
 * @author yennth
 *
 */
@StringMaxLength(100)
public class Subject extends StringPrimitiveValue<Subject>{

	public Subject(String rawValue) {
		super(rawValue);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
