package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.primitive.format;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1, max = 10)
public class SelectionExternalCDLength extends IntegerPrimitiveValue<SelectionExternalCDLength> {

	private static final long serialVersionUID = 1L;

	public SelectionExternalCDLength(int rawValue) {
		super(rawValue);
	}
}
