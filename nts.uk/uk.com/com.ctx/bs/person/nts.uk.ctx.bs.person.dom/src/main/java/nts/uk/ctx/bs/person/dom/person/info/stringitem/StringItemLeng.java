package nts.uk.ctx.bs.person.dom.person.info.stringitem;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max = 1000, min = 1)
public class StringItemLeng extends IntegerPrimitiveValue<StringItemLeng> {

	private static final long serialVersionUID = 1L;

	public StringItemLeng(Integer rawValue) {
		super(rawValue);
	}

}
