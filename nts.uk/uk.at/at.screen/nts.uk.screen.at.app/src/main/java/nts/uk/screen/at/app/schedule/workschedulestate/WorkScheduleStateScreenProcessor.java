package nts.uk.screen.at.app.schedule.workschedulestate;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class WorkScheduleStateScreenProcessor {

	@Inject
	private WorkScheduleStateScreenRepository workScheduleStateScreenRepo;

	public List<WorkScheduleStateScreenDto> getByListSidAndDateAndScheId(WorkScheduleStateScreenParams params) {
		if (params.sId == null || params.sId.size() == 0) {
			return Collections.emptyList();
		} else {
			return this.workScheduleStateScreenRepo.getByListSidAndDateAndScheId(params.sId, params.startDate,
					params.endDate);
		}
	}
}
