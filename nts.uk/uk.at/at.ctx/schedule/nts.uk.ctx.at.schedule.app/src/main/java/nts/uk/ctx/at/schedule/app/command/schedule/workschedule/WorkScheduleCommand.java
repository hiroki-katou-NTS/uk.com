/**
 * 
 */
package nts.uk.ctx.at.schedule.app.command.schedule.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkScheduleCommand {
	
	public String sid;
	public String ymd;
	public String workTypeCd;
	public String workTimeCd;
	public Integer startTime;
	public Integer endTime;
	public String shiftCode;
	public String viewMode;
	public boolean isChangeTime;
	
}
