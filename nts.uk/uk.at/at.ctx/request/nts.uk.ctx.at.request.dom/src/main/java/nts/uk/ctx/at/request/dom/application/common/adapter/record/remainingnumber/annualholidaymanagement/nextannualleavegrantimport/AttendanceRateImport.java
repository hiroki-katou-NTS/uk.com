package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.nextannualleavegrantimport;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 出勤率
 * 
 * @author sonnlb
 */
@DecimalRange(min = "0", max = "100")
public class AttendanceRateImport extends DecimalPrimitiveValue<AttendanceRateImport> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param attendanceRate 出勤率
	 */
	public AttendanceRateImport(Double attendanceRate){
		super(BigDecimal.valueOf(attendanceRate));
	}

	/**
	 * 日数を加算する
	 * 
	 * @param days
	 *            日数
	 * @return 加算後の4週日数
	 */
	public AttendanceRateImport addDays(Double attendanceRate) {
		return new AttendanceRateImport(this.v().doubleValue() + attendanceRate);
	}

}
