package nts.uk.ctx.at.function.dom.alarm.extractionrange.month;
/**
 * 終了月の指定方法
 * @author phongtq
 *
 */
public enum SpecifyEndMonth {
	/** 開始から期間を指定する */
	SPECIFY_PERIOD_FROM_START_MONTH(1, "開始から期間を指定する"), // number months from start to end

	/** 締め終了月を指定する */
	SPECIFY_CLOSE_END_MONTH(2, "締め終了月を指定する"); // the end month

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private SpecifyEndMonth(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
