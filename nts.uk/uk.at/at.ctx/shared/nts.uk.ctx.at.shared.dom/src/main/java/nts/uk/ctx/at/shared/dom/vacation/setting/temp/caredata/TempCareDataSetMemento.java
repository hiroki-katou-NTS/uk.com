package nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public interface TempCareDataSetMemento {
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId);
	
	/**
	 * Sets the schedule record atr.
	 *
	 * @param scheduleRecordAtr the new schedule record atr
	 */
	public void setScheduleRecordAtr(ScheduleRecordAtr scheduleRecordAtr);
	
	/**
	 * Sets the annual leave use.
	 *
	 * @param annualLeaveUse the new annual leave use
	 */
	public void setAnnualLeaveUse(ManagementDays annualLeaveUse);
	
	/**
	 * Sets the time annual leave use private go out.
	 *
	 * @param timeAnnualLeaveUsePrivateGoOut the new time annual leave use private go out
	 */
	public void setTimeAnnualLeaveUsePrivateGoOut(TimeHoliday timeAnnualLeaveUsePrivateGoOut);
	
	/**
	 * Sets the work type code.
	 *
	 * @param workTypeCode the new work type code
	 */
	public void setWorkTypeCode(WorkTypeCode workTypeCode);
	
	/**
	 * Sets the ymd.
	 *
	 * @param ymd the new ymd
	 */
	public void setYmd(GeneralDate ymd);
	

}
