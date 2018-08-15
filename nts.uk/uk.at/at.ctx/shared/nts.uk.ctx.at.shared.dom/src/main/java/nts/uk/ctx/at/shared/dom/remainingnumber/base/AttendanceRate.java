package nts.uk.ctx.at.shared.dom.remainingnumber.base;

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
}
