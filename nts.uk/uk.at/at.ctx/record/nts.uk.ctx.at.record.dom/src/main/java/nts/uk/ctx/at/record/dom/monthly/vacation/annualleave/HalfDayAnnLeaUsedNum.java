package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 半日年休使用数
 * @author shuichu_ishida
 */
@HalfIntegerRange(min = 0, max = 999.5)
public class HalfDayAnnLeaUsedNum extends HalfIntegerPrimitiveValue<HalfDayAnnLeaUsedNum> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public HalfDayAnnLeaUsedNum(Double days) {
		super(days);
	}
}
