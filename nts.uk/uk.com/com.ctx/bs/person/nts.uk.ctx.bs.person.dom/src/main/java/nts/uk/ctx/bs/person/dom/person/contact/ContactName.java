package nts.uk.ctx.bs.person.dom.person.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 連絡先名
 * @author lanlt
 *
 */
@StringMaxLength(20)
public class ContactName extends StringPrimitiveValue<ContactName>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public ContactName(String rawValue) {
		super(rawValue);
	}

}
