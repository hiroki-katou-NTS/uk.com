package nts.uk.ctx.at.record.dom.stamp.management;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 打刻結果表示時間
 * @author phongtq
 *
 */

@IntegerRange(min = 1, max = 360)
public class ResultDisplayTime extends IntegerPrimitiveValue<ResultDisplayTime> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ResultDisplayTime(Integer rawValue) {
		super(rawValue);
	}
}
