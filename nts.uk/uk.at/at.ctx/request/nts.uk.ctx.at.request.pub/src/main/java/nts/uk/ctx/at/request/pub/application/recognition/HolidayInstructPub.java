package nts.uk.ctx.at.request.pub.application.recognition;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface HolidayInstructPub {
	/**
	 * For RequestList231
	 * @param sId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<HolidayInstructExport> acquireBreakIndication(String sId, GeneralDate startDate, GeneralDate endDate);
}
