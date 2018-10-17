package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 年間日数
 * @author shuichi_ishida
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
	
	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 366.0) rawValue = 366.0;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
