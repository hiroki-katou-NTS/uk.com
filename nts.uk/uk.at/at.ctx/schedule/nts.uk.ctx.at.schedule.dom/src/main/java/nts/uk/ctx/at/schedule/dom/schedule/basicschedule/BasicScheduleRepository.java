/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Interface BasicScheduleRepository.
 */
public interface BasicScheduleRepository {

	/**
	 * Get BasicSchedule by primary key
	 * 
	 * @param sId
	 * @param date
	 * @return Optional BasicSchedule
	 */
	Optional<BasicSchedule> find(String sId, GeneralDate date);
	
	List<BasicSchedule> findSomePropertyWithJDBC(List<String> listSid, DatePeriod datePeriod);
	
	List<BasicSchedule> findSomeChildWithJDBC(List<BasicSchedule> listBasicSchedule);

	/**
	 * Check exists BasicSchedule by primary key
	 * 
	 * @param sId
	 * @param date
	 * @return Optional BasicSchedule
	 */
	boolean isExists(String sId, GeneralDate date);

	/**
	 * insert Basic Schedule
	 * 
	 * @param bSchedule
	 */
	void insert(BasicSchedule bSchedule);
	
//	void insertKSU001(BasicSchedule bSchedule);
	
	void insertAll(List<BasicSchedule> listBSchedule);
	
	void insertScheTimeZone(BasicSchedule bSchedule);
	
	void insertScheTime(BasicSchedule bSchedule);
	
	void insertScheBreak(BasicSchedule listBSchedule);
	
	void insertRelateToWorkTimeCd(BasicSchedule bSchedule);

	/**
	 * update Basic Schedule
	 * 
	 * @param bSchedule
	 */
	void update(BasicSchedule bSchedule);
	
//	void updateKSUKSC001(BasicSchedule bSchedule, boolean isUpdateTimeZone, boolean isUpdateBreakTime, boolean isUpdateScheTime);
	
	void updateScheBasicState(BasicSchedule bSchedule);
	
//	void updateKSU001(BasicSchedule bSchedule);
	
	void updateScheTime(BasicSchedule listBSchedule);
	
	void updateScheBreak(BasicSchedule listBSchedule);
	
	void updateAll(List<BasicSchedule> listBSchedule);
	
	void updateConfirmAtr(List<BasicSchedule> listBasicSchedule);


	/**
	 * Change work type code and work time code ( code for Du Do)
	 * 
	 * @param sId
	 *            employee id
	 * @param date
	 *            date
	 * @param workTypeCode
	 *            work type code
	 * @param workTimeCode
	 *            work time code
	 */
	void changeWorkTypeTime(String sId, GeneralDate date, String workTypeCode, String workTimeCode);

	/**
	 * Delete.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 */
	void delete(String employeeId, GeneralDate baseDate, BasicSchedule basicSchedule);
	
	void deleteWithWorkTimeCodeNull(String employeeId, GeneralDate baseDate, BasicSchedule basicSchedule);

	/**
	 * Find child care by id.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	public List<ChildCareSchedule> findChildCareById(String employeeId, GeneralDate baseDate);

	/**
	 * Find person fee by id.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	public List<WorkSchedulePersonFee> findPersonFeeById(String employeeId, GeneralDate baseDate);

	/**
	 * Find work schedule break time (勤務予定休憩時間帯)
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	List<WorkScheduleBreak> findWorkBreakTime(String employeeId, GeneralDate baseDate);

	/**
	 * 
	 * @param sIds
	 * @return GeneralDate
	 */
	GeneralDate findMaxDateByListSid(List<String> sIds);
	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param dateData　期間
	 * @return
	 */
	List<BasicSchedule> getBasicScheduleBySidPeriodDate(String employeeId, DatePeriod dateData);
	
	/**
	 * 
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BasicSchedule> findAllBetweenDate(List<String> sId, GeneralDate startDate, GeneralDate endDate);
	void insertAllScheduleState(List<WorkScheduleState> listWorkScheduleState);
	/**
	 * 検索
	 * @param employeeId 社員ID
	 * @param dateData　リスト
	 * @return
	 */
	List<BasicSchedule> getBasicScheduleBySidPeriodDate(String employeeId, List<GeneralDate> dates);
	
	void removeScheState(String employeeId, GeneralDate baseDate,
			List<WorkScheduleState> listWorkScheduleState);
}
