/**
 * 4:21:30 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
public interface WorkplaceEventRepository {
	
	Optional<WorkplaceEvent> findByPK(String workplaceId, GeneralDate date);

	List<WorkplaceEvent> getWorkplaceEventsByListDate(String workplaceId, List<GeneralDate> lstDate);

	void addEvent(WorkplaceEvent event);

	void updateEvent(WorkplaceEvent event);

	void removeEvent(WorkplaceEvent event);
}
