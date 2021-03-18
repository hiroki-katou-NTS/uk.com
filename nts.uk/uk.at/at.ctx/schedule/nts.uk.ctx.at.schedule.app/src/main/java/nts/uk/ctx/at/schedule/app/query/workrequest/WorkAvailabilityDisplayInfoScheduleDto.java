package nts.uk.ctx.at.schedule.app.query.workrequest;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.workrule.workinghours.TimeSpanForCalcSharedDto;

/**
 * 
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkAvailabilityDisplayInfoScheduleDto {

	private int assignmentMethod;
	
	private List<String> nameList;
	
	private List<TimeSpanForCalcSharedDto> timeZoneList;
	
}
