package nts.uk.ctx.at.function.ac.vacation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.vacation.CurrentSituationImported;
import nts.uk.ctx.at.function.dom.adapter.vacation.UseStatusAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildNursingLeaveMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class UseStatusFinder implements UseStatusAdapter {
	// requestList342
	@Inject
	private ChildNursingLeaveMng childNursingLeaveMng;

	@Override
	public CurrentSituationImported calChildNursOfInPeriod(String cid, String sid, DatePeriod dateData, boolean mode) {
		childNursingLeaveMng.calChildNursOfInPeriod(cid, sid, dateData, mode);
		// TODO Auto-generated method stub
		return null;
	}

}
