/**
 * 4:21:30 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.event;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
public interface WorkplaceEventRepository {

	List<WorkplaceEvent> getListWorkplaceEvent(String companyId, List<GeneralDate> lstDate);

	void addEvent(WorkplaceEvent event);

	void updateEvent(WorkplaceEvent event);

	void removeEvent(WorkplaceEvent event);
}
