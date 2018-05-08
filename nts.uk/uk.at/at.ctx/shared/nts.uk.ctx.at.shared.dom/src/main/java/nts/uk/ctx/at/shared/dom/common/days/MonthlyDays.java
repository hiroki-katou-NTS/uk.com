package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 月間日数
 * @author shuichu_ishida
 */
@HalfIntegerRange(min = 0, max = 31.0)
public class MonthlyDays extends HalfIntegerPrimitiveValue<MonthlyDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public MonthlyDays(Double days){
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の月間日数
	 */
	public MonthlyDays addDays(Double days){
		return new MonthlyDays(this.v() + days);
	}
}
