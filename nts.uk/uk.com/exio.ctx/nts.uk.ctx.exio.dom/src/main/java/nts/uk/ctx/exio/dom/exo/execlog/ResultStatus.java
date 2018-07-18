package nts.uk.ctx.exio.dom.exo.execlog;

/**
 * 外部入出力結果状態
 */
public enum ResultStatus {
	SUCCESS(0, "成功"), 
	INTERRUPTION(1, "中断"), 
	FAILURE(2, "失敗");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ResultStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
