package nts.uk.ctx.at.record.dom.stamp.management;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 打刻ボタン位置NO
 * @author phongtq
 *
 */
@IntegerRange(min = 1, max = 8)
public class ButtonPositionNo  extends IntegerPrimitiveValue<ButtonPositionNo> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ButtonPositionNo(Integer rawValue) {
		super(rawValue);
	}
}

