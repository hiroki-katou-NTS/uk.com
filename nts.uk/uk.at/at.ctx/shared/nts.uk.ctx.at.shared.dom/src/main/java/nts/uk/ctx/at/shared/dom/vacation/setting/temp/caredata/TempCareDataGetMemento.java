package nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

// TODO: Auto-generated Javadoc
/**
 * The Interface TempCareDataGetMemento.
 */
public interface TempCareDataGetMemento {
	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId();
	
	/**
	 * Gets the schedule record atr.
	 *
	 * @return the schedule record atr
	 */
	public ScheduleRecordAtr getScheduleRecordAtr();
	
	/**
	 * Gets the annual leave use.
	 *
	 * @return the annual leave use
	 */
	public ManagementDays getAnnualLeaveUse();
	
	/**
	 * Gets the time annual leave use private go out.
	 *
	 * @return the time annual leave use private go out
	 */
	public TimeHoliday getTimeAnnualLeaveUsePrivateGoOut();
	
	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	public WorkTypeCode getWorkTypeCode();
	
	/**
	 * Gets the ymd.
	 *
	 * @return the ymd
	 */
	public GeneralDate getYmd();
	

}
