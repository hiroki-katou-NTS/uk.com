package nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailholidayinstruction;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * メール本文
 * @author yennth
 *
 */
@StringMaxLength(1000)
public class Content extends StringPrimitiveValue<Content>{
	public Content(String rawValue) {
		super(rawValue);
	}

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;



}
