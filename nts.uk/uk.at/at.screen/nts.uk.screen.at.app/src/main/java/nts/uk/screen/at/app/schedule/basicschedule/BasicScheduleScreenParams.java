package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class BasicScheduleScreenParams {
	public List<String> employeeId;
	public GeneralDate startDate;
	public GeneralDate endDate;
}
