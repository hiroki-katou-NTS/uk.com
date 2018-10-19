/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.common.timerounding;

/**
 * The Enum Unit.
 */
// 単位
public enum Unit {

	/** The rounding time 1min. */
	// 時系列（原則）
	ROUNDING_TIME_1MIN(0, "1分", "Enum_RoundingTime_1Min"),

	/** The rounding time 5min. */
	ROUNDING_TIME_5MIN(1, "5分", "Enum_RoundingTime_5Min"),

	/** The rounding time 6min. */
	ROUNDING_TIME_6MIN(2, "6分", "Enum_RoundingTime_6Min"),

	/** The rounding time 10min. */
	ROUNDING_TIME_10MIN(3, "10分", "Enum_RoundingTime_10Min"),

	/** The rounding time 15min. */
	ROUNDING_TIME_15MIN(4, "15分", "Enum_RoundingTime_15Min"),

	/** The rounding time 20min. */
	ROUNDING_TIME_20MIN(5, "20分", "Enum_RoundingTime_20Min"),

	/** The rounding time 30min. */
	ROUNDING_TIME_30MIN(6, "30分", "Enum_RoundingTime_30Min"),

	/** The rounding time 60min. */
	ROUNDING_TIME_60MIN(7, "60分", "Enum_RoundingTime_60Min");

	
	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static Unit[] values = Unit.values();

	/**
	 * Instantiates a new unit.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private Unit(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the unit
	 */
	public static Unit valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Unit val : Unit.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	public int asTime() {
	switch (this) {
	case ROUNDING_TIME_1MIN: return 1;
	case ROUNDING_TIME_5MIN: return 5;
	case ROUNDING_TIME_6MIN: return 6;
	case ROUNDING_TIME_10MIN: return 10;
	case ROUNDING_TIME_15MIN: return 15;
	case ROUNDING_TIME_20MIN: return 20;
	case ROUNDING_TIME_30MIN: return 30;
	case ROUNDING_TIME_60MIN: return 60;
	default: throw new RuntimeException("invalid value: " + this);
	}
}

	public static enum Direction {
		TO_FORWARD,
		TO_BACK;
	
		public int value() {
			return this == TO_FORWARD ? 1 : -1;
		}
	}

	public int round(int timeAsMinutes, Direction roundingDirection) {
		int minutesInHour = timeAsMinutes % 60;
		int hourPart = timeAsMinutes / 60;
	
		for (int minute = minutesInHour; ; minute += roundingDirection.value()) {
			if (minute % this.asTime() == 0) {
				return hourPart * 60 + minute;
			}
		}
	}
	
	
	/**
	 * 切り捨て
	 * @param timeAsMinutes
	 * @param roundingDirection
	 * @return
	 */
	public int roundDown(int timeAsMinutes) {
		//マイナスの場合
		if(timeAsMinutes<0) {
			// 一旦、プラスの数値にする
			int result = -timeAsMinutes;
			int amari = result % this.asTime();
			if(amari>0) {
				// 切り捨て処理
				result = result - amari + this.asTime();
			}
			//再びマイナスの値に戻す
			return -result;
		}
		//マイナスではない場合
		int amari = timeAsMinutes % this.asTime();
		if(amari>0) {
			//切り捨て処理
			return timeAsMinutes - amari;
		}
		return timeAsMinutes;
	}
	
	/**
	 * 切り上げ
	 * @param timeAsMinutes
	 * @param roundingDirection
	 * @return
	 */
	public int roundUp(int timeAsMinutes) {
		//マイナスの場合
		if(timeAsMinutes<0) {
			// 一旦、プラスの数値にする
			int result = -timeAsMinutes;
			int amari = result % this.asTime();
			if(amari>0) {
				// 切り上げ処理
				result = result - amari;
			}
			//再びマイナスの値に戻す
			return -result;
		}
		//マイナスではない場合
		int amari = timeAsMinutes % this.asTime();
		if(amari>0) {
			//切り上げ処理
			return timeAsMinutes - amari + this.asTime();
		}
		return timeAsMinutes;
	}
	
	/**
	 * 未満切捨以上切上
	 * @param timeAsMinutes
	 * @param roundingDirection
	 * @return
	 */
	public int roundDownOver(int timeAsMinutes) {
		int div = timeAsMinutes/this.asTime();
		//マイナスの場合
		if(timeAsMinutes<0) {
			if((div % 2)==0) {
				return timeAsMinutes - timeAsMinutes % this.asTime();
			}else {
				if(timeAsMinutes % this.asTime() == 0) {
					return timeAsMinutes + this.asTime();
				}else {
					return timeAsMinutes - timeAsMinutes % this.asTime() - this.asTime();
				}
			}
		}
		//マイナスではない場合
		if((div % 2)==0) {
			return timeAsMinutes - timeAsMinutes % this.asTime();
		}else {
			return timeAsMinutes - timeAsMinutes % this.asTime() + this.asTime();
		}
	}

	
}
