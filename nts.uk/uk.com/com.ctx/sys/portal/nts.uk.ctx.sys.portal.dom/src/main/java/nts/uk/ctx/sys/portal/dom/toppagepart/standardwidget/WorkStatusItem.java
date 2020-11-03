package nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページの部品.標準ウィジェット.勤務状況の項目
 * 
 * @author tutt
 *
 */
public enum WorkStatusItem {
	/**
	 * 0 - 日別実績のエラー
	 */
	DAY_ERR_DISPLAY_ATR(0, "日別実績のエラー"),

	/**
	 * 1 - 残業時間
	 */
	OVERTIME_DISPLAY_ATR(1, "残業時間"),

	/**
	 * 2 - フレックス時間
	 */
	FLEX_DISPLAY_ATR(2, "フレックス時間"),

	/**
	 * 3 - 就業時間外深夜時間
	 */
	NIGTH_DISPLAY_ATR(3, " 就業時間外深夜時間"),

	/**
	 * 4 - 休日出勤時間
	 */
	HDTIME_DISPLAY_ATR(4, "休日出勤時間"),

	/**
	 * 5 - 遅刻早退回数
	 */
	LATECOUNT_DISPLAY_ATR(5, "遅刻早退回数"),

	/**
	 * 6 - 年休残数
	 */
	HDPAID_DISPLAY_ATR(6, "年休残数"),

	/**
	 * 7 - 積立年休残数
	 */
	HDSTK_DISPLAY_ATR(7, "積立年休残数"),

	/**
	 * 8 - 代休残数
	 */
	HDCOM_DISPLAY_ATR(8, "代休残数"),

	/**
	 * 9 - 年休残数
	 */
	HDSUB_DISPLAY_ATR(9, "年休残数"),

	/**
	 * 10 - 子の看護残数
	 */
	CHILD_CARE_DISPLAY_ATR(10, "子の看護残数"),

	/**
	 * 11 - 介護残数
	 */
	CARE_DISPLAY_ATR(11, "介護残数"),

	/**
	 * 12 - 特休残数
	 */
	HDSP1_DISPLAY_ATR(12, "特休残数1"),
	
	/**
	 * 13 - 特休残数
	 */
	HDSP2_DISPLAY_ATR(13, "特休残数2"),
	
	/**
	 * 14 - 特休残数
	 */
	HDSP3_DISPLAY_ATR(14, "特休残数3"),
	
	/**
	 * 15 - 特休残数
	 */
	HDSP4_DISPLAY_ATR(15, "特休残数4"),
	
	/**
	 * 16 - 特休残数
	 */
	HDSP5_DISPLAY_ATR(16, "特休残数5"),
	
	/**
	 * 17 - 特休残数
	 */
	HDSP6_DISPLAY_ATR(17, "特休残数6"),
	
	/**
	 * 18 - 特休残数
	 */
	HDSP7_DISPLAY_ATR(18, "特休残数7"),
	
	/**
	 * 19 - 特休残数
	 */
	HDSP8_DISPLAY_ATR(19, "特休残数8"),
	
	/**
	 * 20 - 特休残数
	 */
	HDSP9_DISPLAY_ATR(20, "特休残数9"),
	
	/**
	 * 21 - 特休残数
	 */
	HDSP10_DISPLAY_ATR(21, "特休残数10"),
	
	/**
	 * 22 - 特休残数
	 */
	HDSP11_DISPLAY_ATR(22, "特休残数11"),
	
	/**
	 * 23 - 特休残数
	 */
	HDSP12_DISPLAY_ATR(23, "特休残数12"),
	
	/**
	 * 24 - 特休残数
	 */
	HDSP13_DISPLAY_ATR(24, "特休残数13"),
	
	/**
	 * 25 - 特休残数
	 */
	HDSP14_DISPLAY_ATR(25, "特休残数14"),
	
	/**
	 * 26 - 特休残数
	 */
	HDSP15_DISPLAY_ATR(26, "特休残数15"),
	
	/**
	 * 27 - 特休残数
	 */
	HDSP16_DISPLAY_ATR(27, "特休残数16"),
	
	/**
	 * 28 - 特休残数
	 */
	HDSP17_DISPLAY_ATR(28, "特休残数17"),
	
	/**
	 * 29 - 特休残数
	 */
	HDSP18_DISPLAY_ATR(29, "特休残数18"),
	
	/**
	 * 30 - 特休残数
	 */
	HDSP19_DISPLAY_ATR(30, "特休残数19"),
	
	/**
	 * 31 - 特休残数
	 */
	HDSP20_DISPLAY_ATR(31, "特休残数20");
	

	WorkStatusItem(int type, String name) {
		this.value = type;
		this.name = name;
	}

	public final int value;
	public final String name;
}
