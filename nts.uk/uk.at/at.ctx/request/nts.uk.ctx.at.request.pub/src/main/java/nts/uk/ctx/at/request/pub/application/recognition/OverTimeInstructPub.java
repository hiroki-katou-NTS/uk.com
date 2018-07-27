package nts.uk.ctx.at.request.pub.application.recognition;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface OverTimeInstructPub {
	/**
	 * For RequestList230
	 * 
	 * @param sId
	 * @param instructDate
	 * @return
	 */
	public List<OverTimeInstructExport> acquireOverTimeWorkInstruction(String sId, GeneralDate startDate, GeneralDate endDate);
}
