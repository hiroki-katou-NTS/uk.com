package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.absenceleave;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;

/**
 * 振休申請の反映
 * @author dudt
 *
 */
public interface AbsenceLeaveReflectSche {
	/**
	 * 振休申請の反映
	 * @param param
	 * @return
	 */
	public boolean absenceLeaveReflect(CommonReflectParamSche param);
	/**
	 * 時刻の反映
	 * @param employeeId
	 * @param baseDate
	 * @param workTypeCode
	 * @param startTime
	 * @param endTime
	 */
	public void absenceLeaveStartEndTimeReflect(String employeeId, GeneralDate baseDate, String workTypeCode, Integer startTime, Integer endTime);

}
