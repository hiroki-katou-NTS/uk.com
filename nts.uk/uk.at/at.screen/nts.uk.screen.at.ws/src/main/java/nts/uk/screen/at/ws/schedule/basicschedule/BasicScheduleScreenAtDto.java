package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;

import lombok.Value;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class BasicScheduleScreenAtDto {
	List<BasicScheduleScreenDto> listDataShortName;
	List<BasicScheduleScreenDto> listDataTimeZone;
}
