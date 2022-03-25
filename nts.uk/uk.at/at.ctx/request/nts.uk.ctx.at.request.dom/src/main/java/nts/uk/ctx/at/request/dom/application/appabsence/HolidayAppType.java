package nts.uk.ctx.at.request.dom.application.appabsence;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.休暇申請の種類
 * 
 * @author thanhnx
 *
 */
@AllArgsConstructor
public enum HolidayAppType {
	/**
	 * 年次有休
	 */
	ANNUAL_PAID_LEAVE(0, "年次有休"),
	/**
	 * 代休
	 */
	SUBSTITUTE_HOLIDAY(1, "代休"),
	/**
	 * 欠勤
	 */
	ABSENCE(2, "欠勤"),
	/**
	 * 特別休暇
	 */
	SPECIAL_HOLIDAY(3, "特別休暇"),
	/**
	 * 積立年休
	 */
	YEARLY_RESERVE(4, "積立年休"),
	/**
	 * 休日
	 */
	HOLIDAY(5, "休日"),
	/**
	 * 時間消化
	 */
	DIGESTION_TIME(6, "時間消化");

	public final int value;

	public final String name;
	
	//勤務種類の分類からに変換する
	public static HolidayAppType covertToHoldayType(WorkTypeClassification workType) {
		switch (workType) {
		case AnnualHoliday:
			return HolidayAppType.ANNUAL_PAID_LEAVE;
		case SubstituteHoliday:
			return HolidayAppType.SUBSTITUTE_HOLIDAY;
		case Absence:
			return HolidayAppType.ABSENCE;
		case SpecialHoliday:
			return HolidayAppType.SPECIAL_HOLIDAY;
		case YearlyReserved:
			return HolidayAppType.YEARLY_RESERVE;
		case Holiday:
			return HolidayAppType.HOLIDAY;
		case TimeDigestVacation:
			return HolidayAppType.DIGESTION_TIME;
		default:
			return null;
		}
	}
}
