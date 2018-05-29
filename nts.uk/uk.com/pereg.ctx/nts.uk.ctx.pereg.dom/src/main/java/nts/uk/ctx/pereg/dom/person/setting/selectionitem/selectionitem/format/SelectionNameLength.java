package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.format;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 80)
public class SelectionNameLength extends IntegerPrimitiveValue<SelectionNameLength> {

	private static final long serialVersionUID = 1L;

	public SelectionNameLength(int rawValue) {
		super(rawValue);
	}

}
