package nts.uk.ctx.at.record.dom.monthly;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 勤怠月間日数
 * @author shuichi_ishida
 */
@HalfIntegerRange(min = 0, max = 99.5)
public class AttendanceDaysMonthDom extends HalfIntegerPrimitiveValue<AttendanceDaysMonthDom> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public AttendanceDaysMonthDom(Double days){
		
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の勤怠月間時間
	 */
	public AttendanceDaysMonthDom addDays(Double days){
		
		return new AttendanceDaysMonthDom(this.v() + days);
	}
}
