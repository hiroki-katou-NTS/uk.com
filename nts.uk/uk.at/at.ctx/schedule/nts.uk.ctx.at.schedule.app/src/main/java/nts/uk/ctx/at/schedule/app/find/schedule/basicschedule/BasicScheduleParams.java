package nts.uk.ctx.at.schedule.app.find.schedule.basicschedule;

import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Value
public class BasicScheduleParams {
	public List<String> sId;
	public GeneralDate startDate;
	public GeneralDate endDate;
}
