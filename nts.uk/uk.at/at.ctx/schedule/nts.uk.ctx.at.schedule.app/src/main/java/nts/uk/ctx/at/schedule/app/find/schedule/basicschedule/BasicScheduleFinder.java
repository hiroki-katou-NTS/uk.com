package nts.uk.ctx.at.schedule.app.find.schedule.basicschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class BasicScheduleFinder {

	@Inject
	private BasicScheduleRepository bScheduleRepo;

	public List<BasicScheduleDto> getByListSidAndDate(BasicScheduleParams params) {
		return this.bScheduleRepo.getByListSidAndDate(params.sId, params.startDate, params.endDate).stream()
				.map(x -> BasicScheduleDto.fromDomain(x)).collect(Collectors.toList());
	}

}
