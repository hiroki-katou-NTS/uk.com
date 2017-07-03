/**
 * 2:17:58 PM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.app.command.event;

import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
public class CompanyEventCommand {
	
	public int date;
	
	public String eventName;

	public String state;
}
