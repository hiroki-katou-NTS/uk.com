/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pub.vacation.setting.nursingleave;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface ShNursingLeaveSettingPub.
 */
public interface ShNursingLeaveSettingPub {

	/**
	 * Aggr child nursing remain period.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param period the period
	 * @param mode the mode
	 * @return the child nursing remain export
	 */
	// RequestList206: 期間内の子看護残を集計する
	// ＜INPUT＞・会社ID・社員ID・集計開始日・集計終了日・モード
	ChildNursingRemainExport aggrChildNursingRemainPeriod(String companyId, String employeeId, DatePeriod period, Integer mode);

	/**
	 * Aggr nursing remain period.
	 *
	 * @param companyId the company id
	 * @param employeeId the employee id
	 * @param period the period
	 * @param mode the mode
	 * @return the child nursing remain export
	 */
	// RequestList207: 期間内の介護残を集計する
	// ＜INPUT＞ ・会社ID ・社員ID ・集計開始日 ・集計終了日 ・モード
	ChildNursingRemainExport aggrNursingRemainPeriod(String companyId, String employeeId, DatePeriod period, Integer mode);
}
