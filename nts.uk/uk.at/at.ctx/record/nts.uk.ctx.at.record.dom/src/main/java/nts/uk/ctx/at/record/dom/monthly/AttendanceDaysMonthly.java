package nts.uk.ctx.at.record.dom.monthly;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 勤怠月間日数
 * @author shuichi_ishida
 */
@HalfIntegerRange(min = 0, max = 99.5)
public class AttendanceDaysMonthly extends HalfIntegerPrimitiveValue<AttendanceDaysMonthly> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public AttendanceDaysMonthly(double days){
		
		super(days);
	}
}
