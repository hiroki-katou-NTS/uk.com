package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 出勤率
 * @author sonnlb
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
	
	@Override
	protected BigDecimal reviseRawValue(BigDecimal rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue.doubleValue() > 100.0) rawValue = BigDecimal.valueOf(100.0);
		if (rawValue.doubleValue() < 0.0) rawValue = BigDecimal.valueOf(0.0);
		return super.reviseRawValue(rawValue);
	}
}
