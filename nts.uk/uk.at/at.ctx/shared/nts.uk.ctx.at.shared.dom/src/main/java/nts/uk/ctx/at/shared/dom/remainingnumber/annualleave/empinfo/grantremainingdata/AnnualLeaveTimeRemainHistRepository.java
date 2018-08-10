package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */
public interface AnnualLeaveTimeRemainHistRepository {

	public void addOrUpdate(AnnualLeaveTimeRemainingHistory domain);
	
	public List<AnnualLeaveTimeRemainingHistory> findByCalcDateClosureDate(String employeeId, GeneralDate calculationStartDate, GeneralDate closureStartDate);
	
	public void deleteAfterDate(String employeeId, GeneralDate date);

}
