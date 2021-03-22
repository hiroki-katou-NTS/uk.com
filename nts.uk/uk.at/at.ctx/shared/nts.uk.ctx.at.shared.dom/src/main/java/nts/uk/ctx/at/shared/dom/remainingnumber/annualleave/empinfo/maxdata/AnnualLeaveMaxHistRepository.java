package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.util.Optional;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 *
 * @author HungTT
 *
 */
public interface AnnualLeaveMaxHistRepository {

	public void addOrUpdate(AnnualLeaveMaxHistoryData domain);

	public Optional<AnnualLeaveMaxHistoryData> find(
			String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate);

}
