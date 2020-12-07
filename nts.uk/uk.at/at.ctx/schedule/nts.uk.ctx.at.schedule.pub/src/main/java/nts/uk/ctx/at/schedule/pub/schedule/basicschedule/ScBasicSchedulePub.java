/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pub.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Interface ScBasicSchedulePub.
 */
public interface ScBasicSchedulePub {

	/**
	 * Find by id. RequestList4
	 * 
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the optional
	 */
	public Optional<ScBasicScheduleExport> findById(String employeeId, GeneralDate baseDate);
	
	/**
	 *  RequestList4 New (Update - 14/8/2020)
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public Optional<ScWorkScheduleExport> findByIdNew(String employeeId, GeneralDate baseDate);
	/**
	 * TEAMD reqlist4 9/10/2020
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public Optional<ScWorkScheduleExport_New> findByIdNewV2(String employeeId, GeneralDate baseDate);
	
	public List<ScBasicScheduleExport> findById(List<String> employeeID, DatePeriod date);

	/**
	 * Find work schedule break time (勤務予定休憩時間帯) RequestList351
	 * 
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the optional
	 */
	public List<ScWorkBreakTimeExport> findWorkBreakTime(String employeeId, GeneralDate baseDate);

	/**
	 * 最も未来の勤務予定の年月日を取得する
	 * 
	 * RequestList439
	 * 
	 * @param sIds
	 * @return GeneralDate
	 */
	public GeneralDate acquireMaxDateBasicSchedule(List<String> sIds);
	
	/**
	 * 
	 * 勤務予定の確定状態を取得する
	 * @param employeeID
	 * @param date
	 * @return
	 */
	public List<BasicScheduleConfirmExport> findConfirmById(List<String> employeeID, DatePeriod date);
}
