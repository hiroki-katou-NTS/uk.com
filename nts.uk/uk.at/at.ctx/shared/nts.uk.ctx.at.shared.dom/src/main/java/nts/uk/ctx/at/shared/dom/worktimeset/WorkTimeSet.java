package nts.uk.ctx.at.shared.dom.worktimeset;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * 所定時間設定.
 *
 * @author Doan Duy Hung
 */
// 所定時間設定
@Value
@EqualsAndHashCode(callSuper = false)
public class WorkTimeSet {
	
	/** The company ID. */
	// 会社ID
	private String companyID;
	
	/** The range time day. */
	// １日の範囲時間
	private int rangeTimeDay;
	
	/** The sift CD. */
	// コード
	private String siftCD;
	
	/** The addition set ID. */
	// 所定時間
	private String additionSetID;
	
	/** The night shift atr. */
	// 夜勤区分
	private WorkTimeNightShift nightShiftAtr;
	
	/** The work time day 1. */
	//所定時間帯
	private WorkTimeDay workTimeDay1;
	private WorkTimeDay workTimeDay2;
	
	/** The start date clock. */
	// 日付開始時刻
	private int startDateClock;
	
	/** The predetermine atr. */
	// 残業を含めた所定時間帯を設定する
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
	
}
