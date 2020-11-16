package nts.uk.ctx.sys.shared.dom.user.password;

public enum PassStatus {
	/** 正式 */
	Official(0), 
	/** 初期パスワード */
	InitPassword(1),
	/** リセット */
	Reset(2); 

	public int value;

	private PassStatus(int type) {
		this.value = type;
	}
}
