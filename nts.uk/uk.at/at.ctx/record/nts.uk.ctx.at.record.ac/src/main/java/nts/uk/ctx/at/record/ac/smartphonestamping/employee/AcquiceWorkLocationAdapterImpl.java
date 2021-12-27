package nts.uk.ctx.at.record.ac.smartphonestamping.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter.AcquireWorkLocationEmplAdapter;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Stateless
public class AcquiceWorkLocationAdapterImpl implements AcquireWorkLocationEmplAdapter {
	@Inject
	private WorkplacePub workplacePub;

	@Override
	public String getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date) {
		// [No.650]社員が所属している職場を取得する
		AffWorkplaceHistoryItemExport affWorkplaceHistoryExport = workplacePub.getAffWkpHistItemByEmpDate(employeeID, date);
		if (affWorkplaceHistoryExport != null) {
			return affWorkplaceHistoryExport.getWorkplaceId();
		}
		return null;
	}

}
