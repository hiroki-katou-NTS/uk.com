package nts.uk.ctx.at.request.ac.record.workfixed;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.workrecord.workfixed.WorkFixedPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.workfixed.WorkFixedAdapter;
@Stateless
public class WorkFixedAdapterImpl implements WorkFixedAdapter{
	@Inject
	private WorkFixedPub workFixedPub;
	@Override
	public boolean getEmploymentFixedStatus(String companyID, GeneralDate date, String workPlaceID, int closureID) {
		return this.workFixedPub.getEmploymentFixedStatus(companyID, date, workPlaceID, closureID);
	}

}
