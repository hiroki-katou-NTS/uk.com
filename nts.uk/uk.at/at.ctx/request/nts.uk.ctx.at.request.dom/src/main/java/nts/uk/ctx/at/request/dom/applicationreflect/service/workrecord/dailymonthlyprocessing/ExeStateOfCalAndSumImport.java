package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

public enum ExeStateOfCalAndSumImport {
	/**
	 * 0: 完了
	 */
	DONE(0,"完了"),
	
	/**
	 * 1: 完了（エラーあり）
	 */
	DONE_WITH_ERROR(1,"完了（エラーあり）"),

	/**
	 * 2: 処理中
	 */
	PROCESSING(2,"処理中"),

	/**
	 * 3:実行中止
	 */
	STOPPING(3,"実行中止"),

	/**
	 * 4: 中断開始
	 */
	START_INTERRUPTION(4,"中断開始"),

	/**
	 * 5: 中断終了
	 */
	END_INTERRUPTION(5,"中断終了");

	public final int value;
	
	public String nameId;

	private ExeStateOfCalAndSumImport(int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
