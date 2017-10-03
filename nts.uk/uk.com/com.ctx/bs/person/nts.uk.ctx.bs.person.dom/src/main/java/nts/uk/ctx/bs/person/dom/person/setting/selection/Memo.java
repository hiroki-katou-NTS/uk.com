package nts.uk.ctx.bs.person.dom.person.setting.selection;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
@StringCharType(CharType.ALPHABET)
public class Memo extends StringPrimitiveValue<Memo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Memo(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
