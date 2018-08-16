package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class WorkRestTimeZoneDto {
	private List<AmPmWorkTimezone> listOffdayWorkTimezone;
	private List<AmPmWorkTimezone> listHalfDayWorkTimezone;
}
