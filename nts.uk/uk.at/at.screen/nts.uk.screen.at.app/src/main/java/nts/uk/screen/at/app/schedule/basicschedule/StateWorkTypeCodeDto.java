package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * state: ONE_DAY_REST = 0 MORNING_WORK = 1 AFTERNOON_WORK = 2 ONE_DAY_WORK = 3
 * 
 * @author sonnh1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateWorkTypeCodeDto {
	private String workTypeCode;
	private int state;
}
