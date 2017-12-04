package nts.uk.ctx.pereg.dom.person.info.item;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(value = 30)
public class ItemName extends StringPrimitiveValue<ItemName>{

	private static final long serialVersionUID = 1L;

	public ItemName(String rawValue) {
		super(rawValue);
	}

}
