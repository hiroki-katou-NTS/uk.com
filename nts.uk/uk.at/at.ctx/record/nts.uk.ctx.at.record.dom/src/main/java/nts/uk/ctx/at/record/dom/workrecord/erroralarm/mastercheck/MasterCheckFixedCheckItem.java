package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck;

/**
 *　マスタチェックの固定のチェック項目
 */
public enum MasterCheckFixedCheckItem {
	/**0 ＩＤコード確認	 */
	ID_CODE_CONFIRM(0, "ＩＤコード確認"),
	/**1 年休付与テーブル確認 */
	DAYOFF_GRANT_TBL_CONFIRM(1, "年休付与テーブル確認"),
	/**2 平日時勤務種類確認	 */
	WEEKDAY_WORKTYPE_CONFIRM(2, "平日時勤務種類確認"),
	/**3 平日時就業時間帯確認	 */
	WEEKDAY_WORKTIME_CONFIRM(3, "平日時就業時間帯確認"),
	/**4 休出時勤務種類確認	 */
	HOLIDAY_WORK_WORKTYPE_CONFIRM(4, "休出時勤務種類確認"),
	/**5 休出時就業時間帯確認	 */
	HOLIDAY_WORK_WORKTIME_CONFIRM(5, "休出時就業時間帯確認"),
	/**6 勤務場所確認	 */
	WORKPLACE_CONFIRM(6, "勤務場所確認"),
	/**7 作業①確認	 */
	WORK1_CONFIRM(7, "作業①確認"),
	/**8 作業②確認	 */
	WORK2_CONFIRM(8, "作業②確認"),
	/**9 作業③確認	 */
	WORK3_CONFIRM(9, "作業③確認"),
	/**10 作業④確認	 */
	WORK4_CONFIRM(10, "作業④確認"),
	/**11 作業⑤確認	 */
	WORK5_CONFIRM(11, "作業⑤確認"),
	/**12 休日時勤務種類確認	 */
	HOLIDAY_WORKTYPE_CONFIRM(12, "休日時勤務種類確認");

	public int value;
	
	public String name;
	
	private MasterCheckFixedCheckItem(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public static MasterCheckFixedCheckItem valueOf(int value) {
		for (MasterCheckFixedCheckItem item : MasterCheckFixedCheckItem.values()) {
			if (item.value == value) return item;
		}
		
		return null;
	}
	
	public static MasterCheckFixedCheckItem fromName(String name) {
		for (MasterCheckFixedCheckItem item : MasterCheckFixedCheckItem.values()) {
			if (item.name.equals(name)) return item;
		}
		
		return null;
	}
}
