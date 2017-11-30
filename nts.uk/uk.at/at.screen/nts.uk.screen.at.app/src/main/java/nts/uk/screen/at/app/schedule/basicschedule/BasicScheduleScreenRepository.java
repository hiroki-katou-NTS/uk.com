package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

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
	 * get list data Working employment combination by companyId, list
	 * workTypeCode, list workTimeCode)
	 */
	List<WorkEmpCombineScreenDto> getListWorkEmpCobine(String companyId, List<String> lstWorkTypeCode,
			List<String> lstWorkTimeCode);

	/**
	 * get data ScheduleDisplayControl by companyId
	 * 
	 */
	Optional<ScheduleDisplayControlScreenDto> getScheduleDisControl(String companyId);

	/**
	 * 
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BasicScheduleScreenDto> getDataWorkScheTimezone(List<String> sId, GeneralDate startDate, GeneralDate endDate);
}
