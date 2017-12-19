package nts.uk.ctx.at.record.dom.workrecord.errorsetting;

// 終了状態
public enum OutPutProcess {

	// エラーなし
	NO_ERROR(0),

	// エラーあり
	HAS_ERROR(1);
	
	public int value;

	private OutPutProcess(int value) {
		this.value = value;
	}
}
