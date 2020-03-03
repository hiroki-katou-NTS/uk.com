package nts.uk.ctx.at.record.dom.stamp.management;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 打刻時刻補正時間
 * @author phongtq
 *
 */

@IntegerRange(min = 1, max = 60)
public class CorrectionInterval extends IntegerPrimitiveValue<CorrectionInterval> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public CorrectionInterval(Integer rawValue) {
		super(rawValue);
	}
}
