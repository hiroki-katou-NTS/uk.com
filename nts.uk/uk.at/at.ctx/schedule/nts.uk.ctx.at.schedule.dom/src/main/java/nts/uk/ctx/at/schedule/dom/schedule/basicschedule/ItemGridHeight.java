package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * 明細グリッド高さ
 * 
 * @author sonnh1
 *
 */
@IntegerMaxValue(9999)
@IntegerMinValue(200)
public class ItemGridHeight extends IntegerPrimitiveValue<ItemGridHeight> {
	private static final long serialVersionUID = 1L;

	public ItemGridHeight(Integer rawValue) {
		super(rawValue);
	}
}
