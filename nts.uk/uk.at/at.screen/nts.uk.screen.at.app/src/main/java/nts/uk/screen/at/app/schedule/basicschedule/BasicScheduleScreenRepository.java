package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface BasicScheduleScreenRepository {
	/**
	 * Get data from BasicSchedule base on list Sid, startDate and endDate
	 * 
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BasicScheduleScreenDto> getByListSidAndDate(List<String> sId, GeneralDate startDate, GeneralDate endDate);

	/**
	 * Get data from WorkTime and WorkTimeDay
	 * 
	 * @return
	 */
	List<WorkTimeScreenDto> getListWorkTime(String companyId, int displayAtr);

	/**
	 * Find by companyId and deprecateCls.
	 *
	 * @param companyId
	 * @param deprecateCls
	 * @return List WorkType
	 */
	List<WorkTypeScreenDto> findByCIdAndDeprecateCls(String companyId, int deprecateCls);

	/**
	 * get data Working employment combination by companyId, workType , workTime
	 */
	WorkEmpCombineDto getListWorkEmpCobine(String companyId, String workTypeCode, String workTimeCode);

	/**
	 * get data ScheduleDisplayControl by companyId
	 * 
	 */
	ScheduleDisplayControlDto getListScheduleDisControl(String companyId);
}
