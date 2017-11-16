package nts.uk.screen.at.app.schedule.workschedulestate;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author sonnh1
 *
 */
@Data
public class WorkScheduleStateScreenParams {
	public List<String> sId;
	public GeneralDate startDate;
	public GeneralDate endDate;
}
