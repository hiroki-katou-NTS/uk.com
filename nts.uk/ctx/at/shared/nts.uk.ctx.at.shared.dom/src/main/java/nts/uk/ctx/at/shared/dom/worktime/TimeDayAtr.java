package nts.uk.ctx.at.shared.dom.worktime;

/**
 * 日区分
 * @author Doan Duy Hung
 *
 */

public enum TimeDayAtr {
	
	// 前日
	PreviousDay(0),
	
	// 当日
	TheDay(1),
	
	// 翌日
	NextDay(2),
	
	// 翌々日
	TwoDayLater(3);
	
	public final int value;
	
	TimeDayAtr(int value){
		this.value = value;
	}
}
