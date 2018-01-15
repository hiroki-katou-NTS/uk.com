/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fluidworkset;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.Unit.Direction;

/**
 * The Class TimeRoundingSetting.
 */
@Getter
@AllArgsConstructor
// 時間丸め設定
public class TimeRoundingSetting extends AggregateRoot {

	/** The rounding time. */
	// 単位
	private Unit roundingTime;

	/** The rounding. */
	// 端数処理
	private Rounding rounding;

	/**
	 * 分単位の時間値を設定にもとづき丸める
	 * @param timeAsMinutes　丸め対象の時間
	 * @return 丸めた時間
	 */
	public int round(int timeAsMinutes) {
		
		int minutesInHour = timeAsMinutes % 60;
		
		switch (this.rounding) {
//		case ROUNDING_LESSTHANOREQUAL:
		case ROUNDING_DOWN_OVER:
			int mod = minutesInHour % (this.roundingTime.asTime() * 2);
			val direction = minutesInHour < mod ? Direction.TO_BACK : Direction.TO_FORWARD;
			return this.roundingTime.round(timeAsMinutes, direction);
		case ROUNDING_DOWN:
			return this.roundingTime.round(timeAsMinutes, Direction.TO_BACK);
		case ROUNDING_UP:
			return this.roundingTime.round(timeAsMinutes, Direction.TO_FORWARD);
			
		default:
			throw new RuntimeException("invalid case: " + this.rounding);
		}
	}
}
