package nts.uk.ctx.exio.dom.exo.execlog;

/*
 * 処理区分
 */
public enum ProcessingClassification {

	ERROR(0, "エラー"), 
	END_PROCESSING(1, "処理終了"), 
	START_PROCESSING(2, "処理開始");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ProcessingClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
