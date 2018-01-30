package nts.uk.ctx.bs.person.dom.person.contact;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * メモ
 * @author lanlt
 *
 */
@StringMaxLength(200)
public class Memo extends StringPrimitiveValue<Memo>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public Memo(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
