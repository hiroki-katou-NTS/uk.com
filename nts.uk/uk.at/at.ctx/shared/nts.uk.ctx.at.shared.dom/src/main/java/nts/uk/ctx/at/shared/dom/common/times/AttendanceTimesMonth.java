package nts.uk.ctx.at.shared.dom.common.times;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 勤怠月間回数
 * @author shuichi_ishida
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
	
	/**
	 * 回数を加算する
	 * @param times 回数
	 * @return 加算後の勤怠月間回数
	 */
	public AttendanceTimesMonth addTimes(Integer times){
		
		return new AttendanceTimesMonth(this.v() + times);
	}
	
	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99) rawValue = 99;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}
}
