package nts.uk.ctx.pr.core.dom.itemmaster.itemsalary;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(400)
public class Memo extends StringPrimitiveValue<Memo> {
	public Memo(String value) {
		super(value);

	}

}
