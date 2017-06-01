package nts.uk.ctx.at.shared.dom.worktime;

/**
 * 日勤夜勤区分
 * @author Doan Duy Hung
 *
 */

public enum WorkTimeNightShift {
	
	// 日勤
	DayShift(0),
	
	// 夜勤
	NightShift(1);
	
	public final int value;
	
	WorkTimeNightShift(int value){
		this.value = value;
	}
}
