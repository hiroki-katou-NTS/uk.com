package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.nextannualleavegrantimport;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;
/**
 * 勤怠月間日数
 * @author sonnlb
 */
@HalfIntegerRange(min = 0, max = 99.5)
public class AttendanceDaysMonthImport extends HalfIntegerPrimitiveValue<AttendanceDaysMonthImport> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public AttendanceDaysMonthImport(Double days){
		
		super(days);
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 * @return 加算後の勤怠月間時間
	 */
	public AttendanceDaysMonthImport addDays(Double days){
		
		return new AttendanceDaysMonthImport(this.v() + days);
	}

}
