package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.primitive;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 
 * @author tuannv
 *
 */
@IntegerRange(min = 1, max = 9999)
public class Disporder extends IntegerPrimitiveValue<Disporder>{
	private static final long serialVersionUID = 1L;

	public Disporder(int rawValue) {
		super(rawValue);
	}

}
