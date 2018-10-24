package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 4週日数
 * @author shuichi_ishida
 */
@HalfIntegerRange(min = 0, max = 28.0)
public class FourWeekDays extends HalfIntegerPrimitiveValue<FourWeekDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public FourWeekDays(Double days){
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の4週日数
	 */
	public FourWeekDays addDays(Double days){
		return new FourWeekDays(this.v() + days);
	}
	
	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 28.0) rawValue = 28.0;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
