package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;

public interface AnnualHolidayPlanManaPub {
	/**
	 * 計画年休管理データ pub
	 * @param employeeId
	 * @param workTypeCode
	 * @param dateData
	 * @return
	 */
	List<AnnualHolidayPlan> getDataByPeriod(String employeeId, String workTypeCode, Period dateData);
	/**
	 * 指定する期間の計画年休使用明細を取得する
	 * @param employeeId
	 * @param workTypeCode
	 * @param dateData
	 * @return
	 */
	List<GeneralDate> lstWorkTypePeriod(String employeeId, String workTypeCode, Period dateData);

}
