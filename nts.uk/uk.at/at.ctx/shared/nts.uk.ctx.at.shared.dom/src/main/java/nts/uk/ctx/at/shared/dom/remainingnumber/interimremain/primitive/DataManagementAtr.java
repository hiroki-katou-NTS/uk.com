package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;

import lombok.AllArgsConstructor;

/**
 * 休出管理データ区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum DataManagementAtr {
	/**
	 * 暫定
	 */
	INTERIM(0,"暫定"),
	/**
	 * 確定
	 */
	CONFIRM(1,"確定");
	
	public final Integer value;
	public final String name;

}
