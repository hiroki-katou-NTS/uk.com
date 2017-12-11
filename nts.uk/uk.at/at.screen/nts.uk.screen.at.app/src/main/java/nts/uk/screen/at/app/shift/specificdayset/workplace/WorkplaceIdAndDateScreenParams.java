package nts.uk.screen.at.app.shift.specificdayset.workplace;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class WorkplaceIdAndDateScreenParams {
	public String workplaceId;
	public GeneralDate startDate;
	public GeneralDate endDate;
}
