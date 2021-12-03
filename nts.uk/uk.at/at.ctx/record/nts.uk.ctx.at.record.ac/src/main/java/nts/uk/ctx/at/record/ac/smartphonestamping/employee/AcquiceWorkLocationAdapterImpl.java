package nts.uk.ctx.at.record.ac.smartphonestamping.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter.AcquireWorkLocationEmplAdapter;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.adapter.AffWorkplaceHistoryItemImport;
import nts.uk.ctx.bs.employee.pub.workplace.AffWorkplaceHistoryItemExport;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

@Stateless
public class AcquiceWorkLocationAdapterImpl implements AcquireWorkLocationEmplAdapter {
	@Inject
	private WorkplacePub workplacePub;
	
	@Override
	public AffWorkplaceHistoryItemImport getAffWkpHistItemByEmpDate(String employeeID, GeneralDate date) {
		AffWorkplaceHistoryItemExport affWorkplaceHistoryExport = workplacePub.getAffWkpHistItemByEmpDate(employeeID, date);
		return changeToImport(affWorkplaceHistoryExport);
	}
	
	private AffWorkplaceHistoryItemImport changeToImport (AffWorkplaceHistoryItemExport affWorkplaceHistoryExport) {
		
		AffWorkplaceHistoryItemImport affWorkplaceHistoryItemImport = new AffWorkplaceHistoryItemImport();
		affWorkplaceHistoryItemImport.setHistoryId(affWorkplaceHistoryExport.getHistoryId());
		affWorkplaceHistoryItemImport.setNormalWorkplaceId(affWorkplaceHistoryExport.getNormalWorkplaceId());
		affWorkplaceHistoryItemImport.setWorkplaceId(affWorkplaceHistoryExport.getNormalWorkplaceId());
		return affWorkplaceHistoryItemImport;
	} 
	
	

}
