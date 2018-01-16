package nts.uk.ctx.at.record.dom.monthly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 勤怠月間回数
 * @author shuichu_ishida
 */
@IntegerRange(min = 0, max = 99)
public class AttendanceTimesMonth extends IntegerPrimitiveValue<AttendanceTimesMonth> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param times 回数
	 */
	public AttendanceTimesMonth(Integer times){
		
		super(times);
	}
}
