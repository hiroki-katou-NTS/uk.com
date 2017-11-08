package nts.uk.ctx.at.shared.dom.worktimeset_old;

/**
 * 日区分
 * @author Doan Duy Hung
 *
 */

public enum TimeDayAtr {
	
	// 前日
	Enum_DayAtr_PreviousDay(0),
	
	// 当日
	Enum_DayAtr_Day(1),
	
	// 翌日
	Enum_DayAtr_NextDay(2),
	
	// 翌々日
	Enum_DayAtr_SkipDay(3);
	
	public final int value;
	
	TimeDayAtr(int value){
		this.value = value;
	}
}
