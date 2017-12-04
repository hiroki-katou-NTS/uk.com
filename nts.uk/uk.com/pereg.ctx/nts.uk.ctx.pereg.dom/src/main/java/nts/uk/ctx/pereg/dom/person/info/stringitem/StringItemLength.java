package nts.uk.ctx.pereg.dom.person.info.stringitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 1000, min = 1)
public class StringItemLength extends IntegerPrimitiveValue<StringItemLength> {

	private static final long serialVersionUID = 1L;

	public StringItemLength(Integer rawValue) {
		super(rawValue);
	}

}
