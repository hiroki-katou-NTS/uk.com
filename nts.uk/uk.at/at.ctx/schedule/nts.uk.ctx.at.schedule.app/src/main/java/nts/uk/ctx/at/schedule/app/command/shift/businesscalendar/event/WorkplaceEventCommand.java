/**
 * 2:18:11 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event;

import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
public class WorkplaceEventCommand {
	
	public String workplaceId;

	public int date;

	public String eventName;
	
	public String state;
}
