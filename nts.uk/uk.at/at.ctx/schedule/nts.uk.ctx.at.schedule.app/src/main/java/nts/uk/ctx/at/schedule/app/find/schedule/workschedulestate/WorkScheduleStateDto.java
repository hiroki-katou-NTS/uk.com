package nts.uk.ctx.at.schedule.app.find.schedule.workschedulestate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;

/**
 * 
 * @author sonnh1
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkScheduleStateDto {
	private String sId;
	private int scheduleItemId;
	private int scheduleEditState;
	private GeneralDate date;

	public static WorkScheduleStateDto fromDomain(WorkScheduleState domain) {
		return new WorkScheduleStateDto(domain.getSId(), domain.getScheduleItemId(),
				domain.getScheduleEditState().value, domain.getYmd());
	}
}