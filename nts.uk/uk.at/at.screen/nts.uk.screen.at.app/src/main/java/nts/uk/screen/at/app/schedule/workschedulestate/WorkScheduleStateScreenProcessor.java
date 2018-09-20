package nts.uk.screen.at.app.schedule.workschedulestate;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenParams;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class WorkScheduleStateScreenProcessor {

	@Inject
	private WorkScheduleStateScreenRepository workScheduleStateScreenRepo;

	public List<WorkScheduleStateScreenDto> getByListSidAndDateAndScheId(BasicScheduleScreenParams params) {
		if (params.employeeId == null || params.employeeId.size() == 0) {
			return Collections.emptyList();
		}
		return this.workScheduleStateScreenRepo.getByListSidAndDateAndScheId(params.employeeId, params.startDate,
				params.endDate);
	}
}
