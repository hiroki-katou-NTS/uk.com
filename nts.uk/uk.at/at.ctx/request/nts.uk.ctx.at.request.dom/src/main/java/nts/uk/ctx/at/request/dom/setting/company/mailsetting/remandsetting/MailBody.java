package nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(1000)
public class MailBody extends StringPrimitiveValue<MailBody> {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	public MailBody(String rawValue) {
		super(rawValue);
	}

}
