package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.closurestatus;

import java.util.Optional;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ClosureStatusManagementRequestImport {
	/**
	 * 「締め状態管理」.期間
	 * @param sid
	 * @return
	 */
	Optional<DatePeriod> closureDatePeriod(String sid);
}
