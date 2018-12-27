package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 勤怠月間日数
 * @author shuichi_ishida
 */
@HalfIntegerRange(min = 0, max = 99.5)
public class AttendanceDaysMonth extends HalfIntegerPrimitiveValue<AttendanceDaysMonth> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public AttendanceDaysMonth(Double days){
		
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の勤怠月間時間
	 */
	public AttendanceDaysMonth addDays(Double days){
		
		return new AttendanceDaysMonth(this.v() + days);
	}
	
	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99.5) rawValue = 99.5;
		if (rawValue < 0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
}
