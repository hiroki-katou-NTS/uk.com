package nts.uk.ctx.at.request.ac.record;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.workrecord.closurestatus.GetCalcStartForNextLeaveGrantPub;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.closurestatus.ClosureStatusManagementRequestImport;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ClosureStatusManagementRequestImportImpl implements ClosureStatusManagementRequestImport {
	@Inject
	private GetCalcStartForNextLeaveGrantPub statusMngPub;
	@Override
	public Optional<DatePeriod> closureDatePeriod(String sid) {		
		return statusMngPub.closureDatePeriod(sid);
	}

}
