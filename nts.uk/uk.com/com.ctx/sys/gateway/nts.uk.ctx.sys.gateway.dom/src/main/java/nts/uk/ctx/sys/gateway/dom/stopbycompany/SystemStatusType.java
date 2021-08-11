package nts.uk.ctx.sys.gateway.dom.stopbycompany;
/**
 * 利用停止モード
 * @author sonnlb
 */
public enum SystemStatusType {
	
	/**
	 * 業務運用中
	 */
	RUNNING(0),
	/**
	 * 利用停止前段階
	 */
	IN_PROGRESS(1),
	/**
	 * 利用停止中
	 */
	STOP(2);

	public final int value;

	private SystemStatusType(int value) {
		this.value = value;
	}
}
