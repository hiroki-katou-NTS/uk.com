package nts.uk.ctx.at.shared.dom.worktime;

import java.util.Arrays;
import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;

/**
 * 変更可能な勤務時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.就業時間帯.変更可能な勤務時間帯
 *
 * @author kumiko_otake
 *
 */
@Value
public class ChangeableWorkingTimeZone {

	/** 1日の勤務時間帯リスト **/
	private final List<ChangeableWorkingTimeZonePerNo> forWholeDay;

	/** 午前の勤務時間帯リスト **/
	private final List<ChangeableWorkingTimeZonePerNo> forAm;

	/** 午後の勤務時間帯リスト **/
	private final List<ChangeableWorkingTimeZonePerNo> forPm;

	/** 休出の勤務時間帯リスト **/
	private final List<ChangeableWorkingTimeZonePerNo> forWorkOnDayOff;


	/**
	 * 作る
	 *
	 * @param forWholeDay 1日
	 * @param forAm 午前
	 * @param forPm 午後
	 * @param forWorkOnDayOff 休出
	 * @return
	 */
	public static ChangeableWorkingTimeZone create(
			List<ChangeableWorkingTimeZonePerNo> forWholeDay
		,	List<ChangeableWorkingTimeZonePerNo> forAm
		,	List<ChangeableWorkingTimeZonePerNo> forPm
		,	List<ChangeableWorkingTimeZonePerNo> forWorkOnDayOff
	) {

		// Invariant: List size for 1日, 午前, 午後
		Arrays.asList( forWholeDay, forAm, forPm ).stream()
			.forEach( list -> {
				if ( list.isEmpty() || list.size() > 2 ) {
					throw new RuntimeException("Size of regular working times is out of range. size: " + list.size());
				}
			});

		// Invariant: List size for 休出
		if ( forWorkOnDayOff.size() > 2 ) {
			throw new RuntimeException("Size of work on day times is out of range. size: " + forWorkOnDayOff.size());
		}

		// Invariant: Duplicate of WorkNo
		Arrays.asList( forWholeDay, forAm, forPm, forWorkOnDayOff ).stream()
			.forEach( list -> {
				if (list.size() != list.stream().map( e -> e.getWorkNo() ).distinct().count()) {
					throw new RuntimeException("WorkNo is duplicated.");
				}
			});


		/*
		 * １日の勤務時間帯リスト	： 1日
		 * 午前の勤務時間帯リスト	： 午前
		 * 午後の勤務時間帯リスト	： 午後
		 * 休出の勤務時間帯リスト	： 休出
		 */
		return new ChangeableWorkingTimeZone(forWholeDay, forAm, forPm, forWorkOnDayOff);

	}

	/**
	 * 半日の区別しない
	 *
	 * @param forWholeDay 1日
	 * @param forWorkOnDayOff 休出
	 * @return
	 */
	public static ChangeableWorkingTimeZone createWithoutSeparationOfHalfDay(
			List<ChangeableWorkingTimeZonePerNo> forWholeDay
		,	List<ChangeableWorkingTimeZonePerNo> forWorkOnDayOff
	) {

		// １日・午前・午後 -> 1日 / 休出 -> 休出
		return ChangeableWorkingTimeZone.create(forWholeDay, forWholeDay, forWholeDay, forWorkOnDayOff);

	}


	/**
	 * 出勤日区分による変更可能な時間帯
	 * @param atr
	 * @return 出勤日区分に対応した勤務時間帯リスト
	 */
	public List<ChangeableWorkingTimeZonePerNo> getByAtr(AttendanceDayAttr atr) {

		switch (atr) {
			case FULL_TIME:
				// 1日出勤系→1日の勤務時間帯
				return this.getForWholeDay();
			case HALF_TIME_AM:
				// 午前出勤系→午前の勤務時間帯
				return this.getForAm();
			case HALF_TIME_PM:
				// 午後出勤系→午後の勤務時間帯
				return this.getForPm();
			case HOLIDAY_WORK:
				// 休日出勤→休出の勤務時間帯
				return this.getForWorkOnDayOff();
			default:
				break;
		}

		throw new RuntimeException("Out of Enum: " + atr.toString());
	}
}
