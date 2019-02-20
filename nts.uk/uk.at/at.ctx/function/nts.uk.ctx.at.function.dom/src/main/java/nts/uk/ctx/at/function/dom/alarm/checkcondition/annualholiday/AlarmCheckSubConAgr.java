package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author thanhnx
 * 年休アラームチェック対象者条件
 */
@Getter
@NoArgsConstructor
public class AlarmCheckSubConAgr {

	private boolean narrowUntilNext;

	private boolean narrowLastDay;

	private NumberDayAward numberDayAward;

	private NumOfMonthUnNextHoliday periodUntilNext;

	
	public AlarmCheckSubConAgr(boolean narrowUntilNext, boolean narrowLastDay, int numberDayAward, int periodUntilNext) {
		super();
		this.narrowUntilNext = narrowUntilNext;
		this.narrowLastDay = narrowLastDay;
		this.numberDayAward = new NumberDayAward(numberDayAward);
		this.periodUntilNext = new NumOfMonthUnNextHoliday(periodUntilNext);
	}
}
