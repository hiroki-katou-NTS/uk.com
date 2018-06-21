package nts.uk.ctx.at.shared.dom.vacation.setting.temp.caredata;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ScheduleRecordAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TimeHoliday;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class 介護暫定データ.
 */
@Getter
public class TempCareData extends AggregateRoot {
	
	/** The employee id. */
	private String employeeId;
	
	/** The schedule record atr. */
	private ScheduleRecordAtr scheduleRecordAtr;
	
	/** The annual leave use. */
	private ManagementDays annualLeaveUse;
	
	/** The time annual leave use private go out. */
	private TimeHoliday timeAnnualLeaveUsePrivateGoOut;
	
	/** The work type code. */
	private WorkTypeCode workTypeCode;
	
	/** The ymd. */
	private GeneralDate ymd;
	
	/**
	 * Instantiates a new temp care data.
	 *
	 * @param memento the memento
	 */
	public TempCareData(TempCareDataGetMemento memento) {
		this.employeeId = memento.getEmployeeId();
		this.scheduleRecordAtr = memento.getScheduleRecordAtr();
		this.annualLeaveUse = memento.getAnnualLeaveUse();
		this.timeAnnualLeaveUsePrivateGoOut = memento.getTimeAnnualLeaveUsePrivateGoOut();
		this.workTypeCode = memento.getWorkTypeCode();
		this.ymd = memento.getYmd();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TempCareDataSetMemento memento) {
		memento.setEmployeeId(this.employeeId);
		memento.setScheduleRecordAtr(this.scheduleRecordAtr);
		memento.setAnnualLeaveUse(this.annualLeaveUse);
		memento.setTimeAnnualLeaveUsePrivateGoOut(this.timeAnnualLeaveUsePrivateGoOut);
		memento.setWorkTypeCode(this.workTypeCode);
		memento.setYmd(this.ymd);
	}

}
