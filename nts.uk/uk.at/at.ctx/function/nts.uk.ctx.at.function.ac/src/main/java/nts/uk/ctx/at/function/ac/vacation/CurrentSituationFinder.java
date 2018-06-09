package nts.uk.ctx.at.function.ac.vacation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentSituationAdapter;
import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentSituationImported;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNursingRemainOutputPara;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildNursingLeaveMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class CurrentSituationFinder implements CurrentSituationAdapter {

	@Inject
	private ChildNursingLeaveMng childNursingLeaveMng;

	@Override
	public CurrentSituationImported calChildNursOfInPeriod(String cid, String sid, DatePeriod dateData, boolean mode) {
		// requestList341&&342
		ChildCareNursingRemainOutputPara ChildCareNursing = childNursingLeaveMng.calChildNursOfInPeriod(cid, sid,
				dateData, mode);
		if (ChildCareNursing == null)
			return null;
		return new CurrentSituationImported(ChildCareNursing.getBeforeUseDays(),
				ChildCareNursing.getBeforeCareLeaveDays());
	}
}
