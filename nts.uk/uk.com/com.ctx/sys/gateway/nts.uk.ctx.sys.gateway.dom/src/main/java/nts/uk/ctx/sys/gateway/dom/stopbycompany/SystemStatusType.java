package nts.uk.ctx.sys.gateway.dom.stopbycompany;
/**
 * 利用停止モード
 * @author sonnlb
 */
public enum SystemStatusType {
	/**
	 * 担当者モード
	 */
	PERSON_MODE(1),
	/**
	 * 管理者モード
	 */
	ADMIN_MODE(2);

	public final int value;

	private SystemStatusType(int value) {
		this.value = value;
	}
}
