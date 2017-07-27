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
	 * Get BasicSchedule by primary key
	 * 
	 * @param sId
	 * @param date
	 * @return Optional BasicSchedule
	 */
	Optional<BasicScheduleScreenDto> getByPK(String sId, GeneralDate date);
}
