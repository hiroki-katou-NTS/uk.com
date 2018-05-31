/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.timerounding;

import lombok.Getter;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit.Direction;

/**
 * The Class TimeRoundingSetting.
 */
// 時間丸め設定
@Getter
public class TimeRoundingSetting extends DomainObject {

	/** The rounding time. */
	// 単位
	private Unit roundingTime;

	/** The rounding. */
	// 端数処理
	private Rounding rounding;

	/**
	 * Instantiates a new time rounding setting.
	 *
	 * @param roundingTime
	 *            the rounding time
	 * @param rounding
	 *            the rounding
	 */
	public TimeRoundingSetting(Unit roundingTime, Rounding rounding) {
		this.roundingTime = roundingTime;
		this.rounding = rounding;
	}

	/**
	 * Instantiates a new time rounding setting.
	 *
	 * @param roundingTime
	 *            the rounding time
	 * @param rounding
	 *            the rounding
	 */
	public TimeRoundingSetting(int roundingTime, int rounding) {
		this.roundingTime = EnumAdaptor.valueOf(roundingTime, Unit.class);
		this.rounding = EnumAdaptor.valueOf(rounding, Rounding.class);
	}

	/**
	 * Round.
	 *
	 * @param timeAsMinutes
	 *            the time as minutes
	 * @return the int
	 */
	public int round(int timeAsMinutes) {

		int minutesInHour = timeAsMinutes % 60;

		switch (this.rounding) {
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

	/**
	 * Correct data.
	 *
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(TimeRoundingSetting oldDomain) {
		this.roundingTime = oldDomain.getRoundingTime();
		this.rounding = oldDomain.getRounding();
	}

	/**
	 * Default data rounding up.
	 */
	public void setDefaultDataRoundingUp() {
		this.roundingTime = Unit.ROUNDING_TIME_1MIN;
		this.rounding = Rounding.ROUNDING_UP;
	}

	/**
	 * Default data rounding down.
	 */
	public void setDefaultDataRoundingDown() {
		this.roundingTime = Unit.ROUNDING_TIME_1MIN;
		this.rounding = Rounding.ROUNDING_DOWN;
	}
}
