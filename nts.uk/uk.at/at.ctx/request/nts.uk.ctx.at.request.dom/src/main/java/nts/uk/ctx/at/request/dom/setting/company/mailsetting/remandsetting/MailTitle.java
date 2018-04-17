package nts.uk.ctx.at.request.dom.setting.company.mailsetting.remandsetting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class MailTitle extends StringPrimitiveValue<MailTitle> {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	public MailTitle(String rawValue) {
		super(rawValue);
	}

}