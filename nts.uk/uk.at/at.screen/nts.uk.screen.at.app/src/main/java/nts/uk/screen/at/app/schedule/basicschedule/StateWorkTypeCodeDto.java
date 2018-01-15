package nts.uk.screen.at.app.schedule.basicschedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * There are two types of states
 * 
 * Define type of work of workTypeCode:
 * 
 * state: ONE_DAY_REST = 0 MORNING_WORK = 1 AFTERNOON_WORK = 2 ONE_DAY_WORK = 3
 * 
 * Determine the need of workTimeCode:
 * 
 * state: REQUIRED = 0 OPTIONAL = 1, NOT_REQUIRED = 2
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
