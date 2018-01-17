/**
 * 
 */
package nts.uk.ctx.pereg.dom.setting.favorite;

import lombok.AllArgsConstructor;

/**
 * @author danpv
 *
 */
@AllArgsConstructor
public enum Form {

	//人事台帳
	PERSONAL_LEDGER (0),
	
	//人事異動辞令書
	PERSONAL_CHANGE_ORDER (1),
	
	//退職証明書
	RETIREMENT_CERTIFICATE (2);
	
	public final int value;
}
