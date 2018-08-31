package nts.uk.ctx.at.schedule.dom.schedule.algorithm;

import java.util.Collections;
import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;

/**
 * 
 * @author sonnh1
 *
 */
@Data
public class WorkRestTimeZoneDto {
	private List<AmPmWorkTimezone> listOffdayWorkTimezone;
	private List<AmPmWorkTimezone> listHalfDayWorkTimezone;

	public WorkRestTimeZoneDto(List<AmPmWorkTimezone> listOffdayWorkTimezone,
			List<AmPmWorkTimezone> listHalfDayWorkTimezone) {
		super();
		this.listOffdayWorkTimezone = listOffdayWorkTimezone != null ? listOffdayWorkTimezone : Collections.emptyList();
		this.listHalfDayWorkTimezone = listHalfDayWorkTimezone != null ? listHalfDayWorkTimezone
				: Collections.emptyList();
	}
}
