package nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement;

import nts.arc.time.GeneralDate;

public interface NumberOfRemainingHolidaysPub {

	
	/**
	 * RequestList201
	 * 基準日時点の積立年休残数を取得する
	 * @param employeeId
	 * @param referenceDate
	 * @return Number of remaining holidays as of the base date
	 */
	int NumberOfRemainingHolidays(String employeeId, GeneralDate referenceDate);
}
