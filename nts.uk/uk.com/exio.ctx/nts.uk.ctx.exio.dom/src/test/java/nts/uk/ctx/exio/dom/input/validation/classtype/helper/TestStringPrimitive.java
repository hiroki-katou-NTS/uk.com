package nts.uk.ctx.exio.dom.input.validation.classtype.helper;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(3)
public class TestStringPrimitive extends StringPrimitiveValue<TestStringPrimitive>{
	private static final long serialVersionUID = 1L;

	public TestStringPrimitive(String rawValue) {
		super(rawValue);
	}
}
