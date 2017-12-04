package nts.uk.ctx.bs.person.dom.person.setting.selectionitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<Memo> {

	private static final long serialVersionUID = 1L;

	public Memo(String rawValue) {
		super(rawValue);
	}
}
