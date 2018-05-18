package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 年間日数
 * @author shuichu_ishida
 */
@HalfIntegerRange(min = 0, max = 366.0)
public class YearlyDays extends HalfIntegerPrimitiveValue<YearlyDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public YearlyDays(Double days){
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の年間日数
	 */
	public YearlyDays addDays(Double days){
		return new YearlyDays(this.v() + days);
	}
}
