/**
 * 2:17:58 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.event;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
@Data
public class CompanyEventCommand {
	
	public GeneralDate date;
	
	public String eventName;

	public String state;

	public CompanyEventCommand(GeneralDate date, String eventName, String state) {
		super();
		this.date = date;
		this.eventName = eventName;
		this.state = state;
	}
	
	
}
