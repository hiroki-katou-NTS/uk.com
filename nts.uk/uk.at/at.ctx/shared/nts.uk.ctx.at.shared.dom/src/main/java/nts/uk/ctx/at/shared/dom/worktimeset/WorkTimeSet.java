package nts.uk.ctx.at.shared.dom.worktimeset;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 所定時間設定
 * @author Doan Duy Hung
 *
 */

@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTimeSet {
	
	private String companyID;
	
	private int rangeTimeDay;
	
	private String siftCD;
	
	private String additionSetID;
	
	private WorkTimeNightShift nightShiftAtr;
	
	private WorkTimeDay workTimeDay1;
	
	private WorkTimeDay workTimeDay2;
	
	private int startDateClock;
	
	private int predetermineAtr;

	public WorkTimeSet(String companyID, int rangeTimeDay, String siftCD, String additionSetID,
			WorkTimeNightShift nightShiftAtr, WorkTimeDay workTimeDay1, WorkTimeDay workTimeDay2, int startDateClock,
			int predetermineAtr) {
		super();
		this.companyID = companyID;
		this.rangeTimeDay = rangeTimeDay;
		this.siftCD = siftCD;
		this.additionSetID = additionSetID;
		this.nightShiftAtr = nightShiftAtr;
		this.workTimeDay1 = workTimeDay1;
		this.workTimeDay2 = workTimeDay2;
		this.startDateClock = startDateClock;
		this.predetermineAtr = predetermineAtr;
	}
	
	/**
	 * 1日の範囲を時間帯として返す
	 * @return 1日の範囲(時間帯)
	 */
	public TimeSpanForCalc getOneDaySpan() {
		return new TimeSpanForCalc(new TimeWithDayAttr(startDateClock),new TimeWithDayAttr(startDateClock+rangeTimeDay));
	}
}
