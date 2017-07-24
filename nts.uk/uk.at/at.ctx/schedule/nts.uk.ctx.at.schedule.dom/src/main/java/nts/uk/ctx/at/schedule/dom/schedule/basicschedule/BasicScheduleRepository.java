package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface BasicScheduleRepository {
	/**
	 * Get data from BasicSchedule base on list Sid, startDate and endDate
	 * 
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BasicSchedule> getByListSidAndDate(List<String> sId, GeneralDate startDate, GeneralDate endDate);

	/**
	 * Get BasicSchedule by primary key
	 * 
	 * @param sId
	 * @param date
	 * @return Optional BasicSchedule
	 */
	Optional<BasicSchedule> getByPK(String sId, GeneralDate date);

	/**
	 * insert Basic Schedule
	 * 
	 * @param bSchedule
	 */
	void insertBSchedule(BasicSchedule bSchedule);

	/**
	 * update Basic Schedule
	 * 
	 * @param bSchedule
	 */
	void updateBSchedule(BasicSchedule bSchedule);
}
