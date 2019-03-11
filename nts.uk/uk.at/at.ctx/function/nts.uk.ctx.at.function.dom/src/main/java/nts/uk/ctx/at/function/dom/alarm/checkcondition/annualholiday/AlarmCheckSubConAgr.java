package nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * @author thanhnx
 * 年休アラームチェック対象者条件
 */
@Getter
@NoArgsConstructor
public class AlarmCheckSubConAgr extends AggregateRoot{

	/**
	 * 次回年休付与日までの期間の条件で絞り込む
	 */
	private boolean narrowUntilNext;

	/**
	 * 前回年休付与日数の条件で絞り込む
	 */
	private boolean narrowLastDay;

	/**
	 * 前回年休付与日数
	 */
	private Optional<NumberDayAward> numberDayAward;

	/**
	 * 次回年休付与日までの期間
	 */
	private Optional<NumOfMonthUnNextHoliday> periodUntilNext;

	
	public AlarmCheckSubConAgr(boolean narrowUntilNext, boolean narrowLastDay, Integer numberDayAward, Integer periodUntilNext) {
		super();
		this.narrowUntilNext = narrowUntilNext;
		this.narrowLastDay = narrowLastDay;
		this.numberDayAward = numberDayAward == null ? Optional.empty() : Optional.of(new NumberDayAward(numberDayAward));
		this.periodUntilNext = periodUntilNext == null ? Optional.empty() : Optional.of(new NumOfMonthUnNextHoliday(periodUntilNext));
	}
}
