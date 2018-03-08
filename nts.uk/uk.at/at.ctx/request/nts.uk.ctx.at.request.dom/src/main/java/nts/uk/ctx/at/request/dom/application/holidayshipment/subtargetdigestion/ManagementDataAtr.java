package nts.uk.ctx.at.request.dom.application.holidayshipment.subtargetdigestion;

/**
 * 管理データ区分
 * 
 * @author sonnlb
 */

public enum ManagementDataAtr {
	/**
	 * 暫定
	 */
	INTERIM(0),
	/**
	 * 確定
	 */
	CONFIRM(1);

	public final int value;

	private ManagementDataAtr(int value) {
		this.value = value;
	}
}
