/**
 * 2:18:11 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Data
public class WorkplaceEventCommand {
	
	public String workplaceId;

	public GeneralDate date;

	public String eventName;
	
	public String state;

	public WorkplaceEventCommand(String workplaceId, GeneralDate date, String eventName, String state) {
		super();
		this.workplaceId = workplaceId;
		this.date = date;
		this.eventName = eventName;
		this.state = state;
	}
}
