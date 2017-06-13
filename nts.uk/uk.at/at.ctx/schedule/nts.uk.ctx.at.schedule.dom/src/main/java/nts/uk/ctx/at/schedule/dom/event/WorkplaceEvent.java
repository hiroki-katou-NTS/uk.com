/**
 * 4:16:53 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.event;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;

/**
 * @author hungnm
 *
 */
@Getter
public class WorkplaceEvent extends AggregateRoot {

	WorkplaceId workplaceId;

	GeneralDate date;

	EventName eventName;

	private WorkplaceEvent(WorkplaceId workplaceId, GeneralDate date, EventName eventName) {
		super();
		this.workplaceId = workplaceId;
		this.date = date;
		this.eventName = eventName;
	}

	private WorkplaceEvent() {
		super();
	}

	public static WorkplaceEvent createFromJavaType(String workplaceId, GeneralDate date, String eventName) {
		return new WorkplaceEvent(new WorkplaceId(workplaceId), date, new EventName(eventName));
	}

}
