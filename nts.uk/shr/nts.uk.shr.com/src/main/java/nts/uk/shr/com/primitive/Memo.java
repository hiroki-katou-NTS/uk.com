package nts.uk.shr.com.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * メモ
 */
@StringMaxLength(1000)
public class Memo extends StringPrimitiveValue<Memo> {

	private static final long serialVersionUID = 1L;

	public Memo(String rawValue) {
		super(rawValue);
	}

}
