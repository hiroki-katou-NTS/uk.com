package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class BasicScheduleScreenDto {
	private String employeeId;
	private GeneralDate date;
	private String workTypeCode;
	private String workTimeCode;
}
