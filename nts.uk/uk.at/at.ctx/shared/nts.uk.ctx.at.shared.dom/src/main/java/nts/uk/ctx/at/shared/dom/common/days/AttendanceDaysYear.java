package nts.uk.ctx.at.shared.dom.common.days;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 勤怠年間日数
 * @author shuichu_ishida
 */
@HalfIntegerRange(min = 0, max = 9999.5)
public class AttendanceDaysYear extends HalfIntegerPrimitiveValue<AttendanceDaysYear> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public AttendanceDaysYear(Double days){
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の勤怠月間時間
	 */
	public AttendanceDaysYear addDays(Double days){
		return new AttendanceDaysYear(this.v() + days);
	}
}
