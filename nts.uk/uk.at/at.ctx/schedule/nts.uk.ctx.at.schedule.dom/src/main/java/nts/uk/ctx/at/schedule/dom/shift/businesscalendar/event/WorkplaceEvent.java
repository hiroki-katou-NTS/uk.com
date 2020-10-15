/**
 * 4:16:53 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/**
 * 職場行事
 * @author hungnm
 *
 */
@Getter
public class WorkplaceEvent extends AggregateRoot {

	private String workplaceId;

	private GeneralDate date;

	private EventName eventName;

	private WorkplaceEvent(String workplaceId, GeneralDate date, EventName eventName) {
		super();
		this.workplaceId = workplaceId;
		this.date = date;
		this.eventName = eventName;
	}

	private WorkplaceEvent() {
		super();
	}

	public static WorkplaceEvent createFromJavaType(String workplaceId, GeneralDate date, String eventName) {
		return new WorkplaceEvent(workplaceId, date, new EventName(eventName));
	}

}
