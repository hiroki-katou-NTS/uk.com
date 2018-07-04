package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class WorkRestTimeZoneDto {
	private List<DeductionTime> listOffdayWorkTimezone;
	private List<DeductionTime> listHalfDayWorkTimezone;
}
