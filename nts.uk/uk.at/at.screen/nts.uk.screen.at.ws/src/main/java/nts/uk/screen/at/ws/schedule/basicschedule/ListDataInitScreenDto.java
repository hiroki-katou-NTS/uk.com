package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;

import lombok.Value;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTimeScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTypeScreenDto;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class ListDataInitScreenDto {
	private List<WorkTypeScreenDto> listWorkType;
	private List<WorkTimeScreenDto> listWorkTime;
}
