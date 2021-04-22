package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.アラーム.抽出する範囲.抽出期間（日単位）
 * スケの締め日指定月
 *
 */
public enum ScheSpecifiedMonth {

	/**当月*/
	CURRENTMONTH(0, "当月"),
	
	/** 1ヶ月後 */
	ONEMONTHAGO(1, "1ヶ月後"),

	/** 2ヶ月後*/
	TWOMONTHAGO(2, "2ヶ月後"),
	
	/** 3ヶ月後*/
	THREEMONTHAGO(3, "3ヶ月後"),
	
	/** 4ヶ月後*/
	FOURMONTHAGO(4, "4ヶ月後"),
	
	/** 5ヶ月後*/
	FIVEMONTHAGO(5, "5ヶ月後"),
	
	/** 6ヶ月後*/
	SIXMONTHAGO(6, "6ヶ月後");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private ScheSpecifiedMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
