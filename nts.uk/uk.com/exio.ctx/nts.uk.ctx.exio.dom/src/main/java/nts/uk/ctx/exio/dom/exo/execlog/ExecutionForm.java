package nts.uk.ctx.exio.dom.exo.execlog;

/*
 * 外部入出力実行形態
 */
public enum ExecutionForm {
	AUTOMATIC_EXECUTION(0), // 自動実行
	MANUAL_EXECUTION(1); // 手動実行

	/** The value. */
	public final int value;

	private ExecutionForm(int value) {
		this.value = value;
	}
}
