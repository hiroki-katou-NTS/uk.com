package nts.uk.ctx.sys.gateway.dom.stopbycompany;

/**
 * システム利用状態
 * @author sonnlb
 */
public enum UsageStopModeType {

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

	private UsageStopModeType(int value) {
		this.value = value;
	}

}
