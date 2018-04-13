package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 出勤率
 * @author shuichu_ishida
 */
@DecimalRange(min = "0", max = "100")
public class AttendanceRate extends DecimalPrimitiveValue<AttendanceRate> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param attendanceRate 出勤率
	 */
	public AttendanceRate(Double attendanceRate){
		super(BigDecimal.valueOf(attendanceRate));
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の4週日数
	 */
	public AttendanceRate addDays(Double attendanceRate){
		return new AttendanceRate(this.v().doubleValue() + attendanceRate);
	}
}
