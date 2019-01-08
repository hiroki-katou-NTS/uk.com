package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * 
 * @author HungTT
 *
 */
public interface AnnualLeaveRemainHistRepository {

	public void addOrUpdate(AnnualLeaveRemainingHistory domain);
	
	public void delete(String employeeId, YearMonth ym, ClosureId closureId, ClosureDate closureDate);

}
