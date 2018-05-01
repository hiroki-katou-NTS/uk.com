package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 週間日数
 * @author shuichu_ishida
 */
@HalfIntegerRange(min = 0, max = 7.0)
public class WeeklyDays extends HalfIntegerPrimitiveValue<WeeklyDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public WeeklyDays(Double days){
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の週間日数
	 */
	public WeeklyDays addDays(Double days){
		return new WeeklyDays(this.v() + days);
	}
}
