package nts.uk.ctx.exio.dom.exo.execlog;

/*
 * 外部入出力実行形態
 */
public enum ExecutionForm {
	AUTOMATIC_EXECUTION(0, "自動実行"), 
	MANUAL_EXECUTION(1, "手動実行");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ExecutionForm(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
