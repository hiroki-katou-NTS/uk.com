package nts.uk.ctx.at.record.dom.monthly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 勤怠月間回数
 * @author shuichu_ishida
 */
@IntegerRange(min = 0, max = 99)
public class AttendanceTimesMonthDom extends IntegerPrimitiveValue<AttendanceTimesMonthDom> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param times 回数
	 */
	public AttendanceTimesMonthDom(Integer times){
		
		super(times);
	}
	
	/**
	 * 回数を加算する
	 * @param times 回数
	 * @return 加算後の勤怠月間回数
	 */
	public AttendanceTimesMonthDom addTimes(Integer times){
		
		return new AttendanceTimesMonthDom(this.v() + times);
	}
}
