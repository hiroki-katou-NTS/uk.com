package nts.uk.ctx.exio.dom.exo.executionlog;

public enum ExIoOperationState {
	
	/**
	 * 準備中
	 */
	IN_PREPARATION(0),	
	/**
	 * 出力中
	 */
	EXPORTING(1),
	/**
	 * 受入中
	 */
	IMPORTING(2),
	/**
	 * テスト完了
	 */
	TEST_FINISH(3),
	/**
	 * 中断終了
	 */
	INTER_FINISH(4),
	/**
	 * 異常終了
	 */
	FAULT_FINISH(5),
	/**
	 * チェック中
	 */
	CHECKING(6),
	/**
	 * 出力完了
	 */
	EXPORT_FINISH(7),
	
	/**
	 * 受入完了
	 */
	IMPORT_FINISH(8);
	
	/** The value. */
	public final int value;

	private ExIoOperationState(int value) {
		this.value = value;
	}
}
