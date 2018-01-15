/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.authfuncrest;

import java.util.List;

import lombok.Data;

/**
 * @author danpv
 *
 */
@Data
public class AuthFuncRestrictionCommand {
	
	private String roleId;

	private List<AuthFuncRest> authFuncRests;
	
}
