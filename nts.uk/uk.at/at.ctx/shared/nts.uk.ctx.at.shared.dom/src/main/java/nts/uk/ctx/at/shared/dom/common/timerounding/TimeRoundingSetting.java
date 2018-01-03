/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
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
public class TimeRoundingSetting extends DomainObject{
	
	/** The rounding time. */
	// 単位
	private Unit roundingTime;
	
	/** The rounding. */
	// 端数処理
	private Rounding rounding;

	/**
	 * Instantiates a new time rounding setting.
	 *
	 * @param roundingTime the rounding time
	 * @param rounding the rounding
	 */
	public TimeRoundingSetting(Unit roundingTime, Rounding rounding) {
		this.roundingTime = roundingTime;
		this.rounding = rounding;
	}
	
	public TimeRoundingSetting(int roundingTime, int rounding) {
		this.roundingTime = EnumAdaptor.valueOf(roundingTime, Unit.class);
		this.rounding = EnumAdaptor.valueOf(rounding, Rounding.class);
	}
	
	/**
	 * 分単位の時間値を設定にもとづき丸める
	 * @param timeAsMinutes　丸め対象の時間
	 * @return 丸めた時間
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
	 * Restore data.
	 *
	 * @param oldDomain the old domain
	 */
	public void restoreData(TimeRoundingSetting oldDomain) {
		this.roundingTime = oldDomain.getRoundingTime();
		this.rounding = oldDomain.getRounding();
	}
}
