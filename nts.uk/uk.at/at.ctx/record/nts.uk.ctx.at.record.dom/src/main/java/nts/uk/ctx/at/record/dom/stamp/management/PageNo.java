package nts.uk.ctx.at.record.dom.stamp.management;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 打刻ページNO
 * @author phongtq
 *
 */
@IntegerRange(min = 1, max = 5)
public class PageNo extends IntegerPrimitiveValue<PageNo> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public PageNo(Integer rawValue) {
		super(rawValue);
	}
}

