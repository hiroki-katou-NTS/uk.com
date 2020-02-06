package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AnnualHolidayPlanManaPub {
	/**
	 * 計画年休管理データ pub
	 * @param employeeId
	 * @param workTypeCode
	 * @param dateData
	 * @return
	 */
	List<AnnualHolidayPlan> getDataByPeriod(String employeeId, String workTypeCode, DatePeriod dateData);
	/**
	 * 指定する期間の計画年休使用明細を取得する
	 * @param employeeId
	 * @param workTypeCode
	 * @param dateData
	 * @return
	 */
	List<GeneralDate> lstWorkTypePeriod(String employeeId, String workTypeCode, DatePeriod dateData);
	/**
	 * 
	 * @param employeeId
	 * @param workTypeCode
	 * @param period
	 * @return
	 */
	List<GeneralDate> lstRecordByWorkType(String employeeId, String workTypeCode, DatePeriod period);

}
