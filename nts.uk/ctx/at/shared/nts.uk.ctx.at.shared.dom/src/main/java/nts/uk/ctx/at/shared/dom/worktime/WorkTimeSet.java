package nts.uk.ctx.at.shared.dom.worktime;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 所定時間設定
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTimeSet {
	
	private String companyID;
	
	private String workTimeSetCD;
	
	private String workTimeCD;
	
	private int rangeTimeDay;
	
	private String additionSetCD;
	
	private WorkTimeNightShift nightShiftAtr;
	
	private int startDateTime;
	
	private TimeDayAtr startDateAtr;
	
	private WorkTimeDay workTimeDay;
	
	public WorkTimeSet(String companyID, String workTimeSetCD, String workTimeCD, int rangeTimeDay,
			String additionSetCD, WorkTimeNightShift nightShiftAtr, int startDateTime, TimeDayAtr startDateAtr, WorkTimeDay workTimeDay) {
		super();
		this.companyID = companyID;
		this.workTimeSetCD = workTimeSetCD;
		this.workTimeCD = workTimeCD;
		this.rangeTimeDay = rangeTimeDay;
		this.additionSetCD = additionSetCD;
		this.nightShiftAtr = nightShiftAtr;
		this.startDateTime = startDateTime;
		this.startDateAtr = startDateAtr;
		this.workTimeDay = workTimeDay;
	}
}
