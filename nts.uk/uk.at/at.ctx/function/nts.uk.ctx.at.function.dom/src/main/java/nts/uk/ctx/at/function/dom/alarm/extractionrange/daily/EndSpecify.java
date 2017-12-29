package nts.uk.ctx.at.function.dom.alarm.extractionrange.daily;

/**
 * @author thanhpv
 * 終了日の指定方法
 */
public enum EndSpecify {

	/**
	 * 実行日からの日数を指定する
	 */
	DAYS(0, "実行日からの日数を指定する"),
	
	/**
	 * 締め日を指定する
	 */
	MONTH(1, "締め日を指定する");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private EndSpecify(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
