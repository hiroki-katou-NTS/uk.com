package nts.uk.ctx.at.shared.dom.alarmList;

import nts.arc.i18n.I18NText;

/**
 * @author dxthuong
 * アラームリストのカテゴリ
 */
public enum AlarmCategory {

	/**
	 * スケジュール日次
	 */
	SCHEDULE_DAILY(0,  I18NText.getText("KAL010_1000")),
	/**
	 * スケジュール週次
	 */
	SCHEDULE_WEEKLY(1, I18NText.getText("KAL010_1500")),
	/**
	 * スケジュール4週
	 */
	SCHEDULE_4WEEK(2, I18NText.getText("KAL010_200")),
	/**
	 * スケジュール月次
	 */
	SCHEDULE_MONTHLY(3, I18NText.getText("KAL010_1100")),
	/**
	 * スケジュール年間
	 */
	SCHEDULE_YEAR(4,  I18NText.getText("KAL010_1200")),
	/**
	 * 日次
	 */
	DAILY(5,  I18NText.getText("KAL010_1")),
	/**
	 * 週次
	 */
	WEEKLY(6, I18NText.getText("KAL010_1300")),
	/**
	 * 月次
	 */
	MONTHLY(7, I18NText.getText("KAL010_100")),
	/**
	 * 申請承認
	 */
	APPLICATION_APPROVAL(8, I18NText.getText("KAL010_500")),
	/**
	 * 複数月
	 */
	MULTIPLE_MONTH(9, I18NText.getText("KAL010_250")),
	/**
	 * 任意期間
	 */
	ANY_PERIOD(10, I18NText.getText("KAL010_607")),
	/**
	 * 年休
	 */
	ATTENDANCE_RATE_FOR_HOLIDAY(11,  I18NText.getText("KAL010_400")),
	/**
	 * ３６協定
	 */
	AGREEMENT(12,  I18NText.getText("KAL010_117")),
	/**
	 * 工数チェック
	 */
	MAN_HOUR_CHECK(13, I18NText.getText("KAL010_1400")),
	/**
	 * マスタチェック
	 */
	MASTER_CHECK(14, I18NText.getText("KAL010_550"));
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;


	
	private AlarmCategory(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
