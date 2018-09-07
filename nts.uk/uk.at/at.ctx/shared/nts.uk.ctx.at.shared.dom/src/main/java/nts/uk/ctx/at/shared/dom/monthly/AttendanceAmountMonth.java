package nts.uk.ctx.at.record.dom.monthly;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 勤怠月間金額
 * @author shuichu_ishida
 */
@IntegerRange(min = 0, max = 99999999)
public class AttendanceAmountMonthDom extends IntegerPrimitiveValue<AttendanceAmountMonthDom> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param amount 金額
	 */
	public AttendanceAmountMonthDom(Integer amount){
		
		super(amount);
	}
	
	/**
	 * 金額を加算する
	 * @param amount 金額
	 * @return 加算後の勤怠月間金額
	 */
	public AttendanceAmountMonthDom addAmount(Integer amount){
	
		return new AttendanceAmountMonthDom(this.v() + amount);
	}
}
