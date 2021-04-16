package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;

/**
 * 
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム.抽出する範囲.抽出期間（月単位）
 * スケの基準月
 *
 */
public enum ScheBaseMonth {
	/**当月*/
	CURRENT_MONTH(0, "当月"),
	
	/** 1ヶ月後 */
	ONE_MONTH_AGO(1, "1ヶ月後"),

	/** 2ヶ月後*/
	TWO_MONTH_AGO(2, "2ヶ月後"),
	
	/** 3ヶ月後*/
	THREE_MONTH_AGO(3, "3ヶ月後"),
	
	/** 4ヶ月後*/
	FOUR_MONTH_AGO(4, "4ヶ月後"),
	
	/** 5ヶ月後*/
	FIVE_MONTH_AGO(5, "5ヶ月後"),
	
	/** 6ヶ月後*/
	SIX_MONTH_AGO(6, "6ヶ月後"),
	
	/** 7ヶ月後*/
	SEVEN_MONTH_AGO(7, "7ヶ月後"),

	/** 8ヶ月後 */
	EIGHT_MONTH_AGO(8, "8ヶ月後"),

	/** 9ヶ月後 */
	NINE_MONTH_AGO(9, "9ヶ月後"),

	/** 10ヶ月後 */
	TEN_MONTH_AGO(10, "10ヶ月後"),

	/** 11ヶ月後 */
	ELEVEN_MONTH_AGO(11, "11ヶ月後"),

	/** 12ヶ月後 */
	TWELVE_MONTH_AGO(12, "12ヶ月後");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private ScheBaseMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
