package nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.timestamp;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;

public interface ChangeAttendanceTimeStamp {

	public void change(String companyId, WorkStamp workStampOld, WorkStamp workStampNew);
}
