package nts.uk.ctx.at.shared.dom.schedule.basicschedule;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;

/**
  * The Enum WorkStyle.
  */
 // 出勤休日区分
public enum WorkStyle {

	/**
	 *  1日休日系
	 */
	ONE_DAY_REST(0),

	/**
	 *  午前出勤系
	 */
	MORNING_WORK(1),

	/**
	 *  午後出勤系
	 */
	AFTERNOON_WORK(2),

	/**
	 *  1日出勤系
	 */
	ONE_DAY_WORK(3);

	public int value;

	private WorkStyle(int value) {
		this.value = value;
	}


	/**
	 * 午前午後区分に変換
	 *
	 * @return
	 */
	public Optional<AmPmAtr> toAmPmAtr() {

		switch( this  ) {
			case ONE_DAY_WORK:		// 1日出勤系
				// 午前午後区分→1日
				return Optional.of( AmPmAtr.ONE_DAY );
			case MORNING_WORK:		// 午前出勤系
				// 午前午後区分→午前
				return Optional.of( AmPmAtr.AM );
			case AFTERNOON_WORK:	// 午後出勤系
				// 午前午後区分→午後
				return Optional.of( AmPmAtr.PM );
			default:
				return Optional.empty();
		}

	}
}
