package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 週間日数
 * @author shuichi_ishida
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
	
	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 7.0) rawValue = 7.0;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
