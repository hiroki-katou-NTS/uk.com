package nts.uk.ctx.at.record.dom.monthly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 勤怠月間金額
 * @author shuichu_ishida
 */
@IntegerRange(min = 0, max = 99999999)
public class AttendanceAmountMonth extends IntegerPrimitiveValue<AttendanceAmountMonth> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param amount 金額
	 */
	public AttendanceAmountMonth(Integer amount){
		
		super(amount);
	}
}
