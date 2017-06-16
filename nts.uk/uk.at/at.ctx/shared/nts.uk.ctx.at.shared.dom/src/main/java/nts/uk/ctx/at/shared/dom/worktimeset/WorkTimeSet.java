package nts.uk.ctx.at.shared.dom.worktimeset;

import java.util.List;

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
	
	private int rangeTimeDay;
	
	private String workTimeSetID;
	
	private String additionSetID;
	
	private WorkTimeNightShift nightShiftAtr;
	
	private List<WorkTimeDay> workTimeDay;
	
	private int startDateClock;
	
	private int predetermineAtr;

	public WorkTimeSet(String companyID, int rangeTimeDay, String workTimeSetID, String additionSetID,
			WorkTimeNightShift nightShiftAtr, List<WorkTimeDay> workTimeDay, int startDateClock, int predetermineAtr) {
		super();
		this.companyID = companyID;
		this.rangeTimeDay = rangeTimeDay;
		this.workTimeSetID = workTimeSetID;
		this.additionSetID = additionSetID;
		this.nightShiftAtr = nightShiftAtr;
		this.workTimeDay = workTimeDay;
		this.startDateClock = startDateClock;
		this.predetermineAtr = predetermineAtr;
	}
}
