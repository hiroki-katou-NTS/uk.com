package nts.uk.ctx.at.schedule.dom.schedule.basicschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
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

	/**
	 * insert Basic Schedule
	 * 
	 * @param bSchedule
	 */
	void insert(BasicSchedule bSchedule);

	/**
	 * update Basic Schedule
	 * 
	 * @param bSchedule
	 */
	void update(BasicSchedule bSchedule);
}
