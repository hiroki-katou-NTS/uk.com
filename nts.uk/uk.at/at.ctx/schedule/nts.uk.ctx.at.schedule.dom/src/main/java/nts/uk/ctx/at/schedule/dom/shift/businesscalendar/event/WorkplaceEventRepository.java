/**
 * 4:21:30 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author hungnm
 *
 */
public interface WorkplaceEventRepository {
	
	Optional<WorkplaceEvent> findByPK(String workplaceId, BigDecimal date);

	List<WorkplaceEvent> getWorkplaceEventsByListDate(String workplaceId, List<BigDecimal> lstDate);

	void addEvent(WorkplaceEvent event);

	void updateEvent(WorkplaceEvent event);

	void removeEvent(WorkplaceEvent event);
}
