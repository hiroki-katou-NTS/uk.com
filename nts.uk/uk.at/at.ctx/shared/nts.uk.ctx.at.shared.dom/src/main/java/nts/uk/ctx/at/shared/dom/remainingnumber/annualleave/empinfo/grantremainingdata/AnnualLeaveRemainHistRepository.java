package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * 
 * @author HungTT
 *
 */
public interface AnnualLeaveRemainHistRepository {

	public void addOrUpdate(AnnualLeaveRemainingHistory domain);
	
	public void delete(String employeeId, YearMonth ym, ClosureId closureId, ClosureDate closureDate);

}
