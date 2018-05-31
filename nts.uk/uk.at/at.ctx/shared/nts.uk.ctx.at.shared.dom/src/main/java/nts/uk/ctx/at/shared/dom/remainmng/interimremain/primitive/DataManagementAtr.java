package nts.uk.ctx.at.shared.dom.remainmng.interimremain.primitive;

import lombok.AllArgsConstructor;

/**
 * 休出管理データ区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum DataManagementAtr {
	INTERIM(0,""),
	CONFIRM(1,"");
	
	public final Integer values;
	public final String name;

}
