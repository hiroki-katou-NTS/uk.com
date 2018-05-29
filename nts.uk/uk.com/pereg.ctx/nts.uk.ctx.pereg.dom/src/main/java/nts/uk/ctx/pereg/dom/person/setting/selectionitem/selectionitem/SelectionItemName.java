package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(30)
public class SelectionItemName extends StringPrimitiveValue<SelectionItemName> {

	private static final long serialVersionUID = 1L;

	public SelectionItemName(String rawValue) {
		super(rawValue);
	}
}
