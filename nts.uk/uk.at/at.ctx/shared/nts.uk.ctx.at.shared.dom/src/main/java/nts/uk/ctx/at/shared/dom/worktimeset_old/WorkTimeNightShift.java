package nts.uk.ctx.at.shared.dom.worktimeset_old;

/**
 * 日勤夜勤区分
 * @author Doan Duy Hung
 *
 */

public enum WorkTimeNightShift {
	
	// 日勤
	Day_Shift(0),
	
	// 夜勤
	Night_Shift(1);
	
	public final int value;
	
	WorkTimeNightShift(int value){
		this.value = value;
	}
}
