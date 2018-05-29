package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<Memo> {

	private static final long serialVersionUID = 1L;

	public Memo(String rawValue) {
		super(rawValue);
	}
}
