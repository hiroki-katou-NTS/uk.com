/**
 * 
 */
package nts.uk.ctx.at.record.app.find.workrecord.authfuncrest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author danpv
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRoleDto {

	private String roleId;
	
	private String roleCode;
	
	private String roleName;
	
}
