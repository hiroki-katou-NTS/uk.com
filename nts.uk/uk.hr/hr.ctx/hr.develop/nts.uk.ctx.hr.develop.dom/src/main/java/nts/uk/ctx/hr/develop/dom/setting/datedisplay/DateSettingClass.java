package nts.uk.ctx.hr.develop.dom.setting.datedisplay;

/**
 * @author anhdt
 * 期間設定区分
 */
public enum DateSettingClass {

	UNSPECIFIED(0, "指定なし"),

	TODAY(1, "今日（システム日付）"),

	DAYS_BEFORE_TODAY(2, "今日からの前日数"),

	MONTHS_BEFORE_TODAY(3, "今日からの前月数"),

	DAYS_AFTER_TODAY(4, "今日からの後日数"),

	MONTHS_AFTER_TODAY(5, "今日からの後月数"),

	DESIGNATED_DATE_OF_YEAR(6, "当年の指定月日"),

	DESIGNATED_DATE_OF_NEXT_YEAR(7, "翌年の指定月日"),

	CURRENT_YEAR_START_DATE(8, "当年の年度開始日"),

	NEXT_YEAR_START_DATE(9, "翌年の年度開始日"),

	CURRENT_YEAR_END_DATE(10, "当年の年度終了日"),

	NEXT_YEAR_END_DATE(11, "翌年の年度終了日");

	public int value;

	public String name;

	DateSettingClass(int type, String name) {
		this.value = type;
		this.name = name;
	}
}
