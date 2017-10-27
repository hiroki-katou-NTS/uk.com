/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.authfuncrest;

import lombok.Data;

/**
 * @author danpv
 *
 */
@Data
public class AuthFuncRest {
	
	/**
	 * 日別実績の機能NO
	 */
	private int functionNo;

	/**
	 * 利用区分
	 */
	private boolean availability;
}
