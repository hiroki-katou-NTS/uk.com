package nts.uk.screen.at.app.schedule.basicschedule;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.DisplayAtr;
import nts.uk.shr.com.context.AppContexts;

/**
 * Get data DB BASIC_SCHEDULE, WORKTIME, WORKTYPE not through dom layer
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
	
	/**
	 * Find by companyId and DeprecateClassification = Deprecated (added by
	 * sonnh1)
	 * 
	 * @return List WorkTypeDto
	 */
	public List<WorkTypeScreenDto> findByCIdAndDeprecateCls() {
		String companyId = AppContexts.user().companyId();
		return this.bScheduleScreenRepo.findByCIdAndDeprecateCls(companyId, DeprecateClassification.NotDeprecated.value);
	}
}
