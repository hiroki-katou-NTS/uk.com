package nts.uk.screen.at.app.shift.workcycle;
/**
 * 起動モード
 * @author khai.dh
 */
public enum BootMode {
	/**
	 * 参照モード
	 */
	REF_MODE(0),
	/**
	 * 実行モード
	 */
	EXEC_MODE(1);

	public final int value;

	BootMode(int value) {
		this.value = value;
	}
}
