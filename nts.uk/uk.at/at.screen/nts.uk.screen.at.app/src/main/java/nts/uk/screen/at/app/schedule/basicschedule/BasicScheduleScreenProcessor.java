package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.DisplayAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * Get data DB BASIC_SCHEDULE, WORKTIME, not through dom layer
 * 
 * @author sonnh1
 *
 */

@Stateless
public class BasicScheduleScreenProcessor {
	@Inject
	private BasicScheduleScreenRepository bScheduleScreenRepo;

	public List<BasicScheduleScreenDto> getByListSidAndDate(BasicScheduleScreenParams params) {
		return this.bScheduleScreenRepo.getByListSidAndDate(params.sId, params.startDate, params.endDate);
	}

	public List<WorkTimeScreenDto> getListWorkTime() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.getListWorkTime(companyId, DisplayAtr.DisplayAtr_Display.value);
	}
}
