package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author sonnh1
 *
 */

@Stateless
public class BasicScheduleScreenFinder {
	@Inject
	private BasicScheduleScreenRepository bScheduleScreenRepo;

	public List<BasicScheduleScreenDto> getByListSidAndDate(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getByListSidAndDate(params.sId, params.startDate, params.endDate);
	}
}
