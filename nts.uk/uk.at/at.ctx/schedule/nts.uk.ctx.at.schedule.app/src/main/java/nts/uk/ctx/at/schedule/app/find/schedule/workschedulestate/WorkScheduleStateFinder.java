package nts.uk.ctx.at.schedule.app.find.schedule.workschedulestate;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class WorkScheduleStateFinder {
	@Inject
	private WorkScheduleStateRepository workScheduleStateRepo;

	public List<WorkScheduleStateDto> findAll() {
		return workScheduleStateRepo.findAll().stream().map(x -> WorkScheduleStateDto.fromDomain(x)).collect(Collectors.toList());
	}
}