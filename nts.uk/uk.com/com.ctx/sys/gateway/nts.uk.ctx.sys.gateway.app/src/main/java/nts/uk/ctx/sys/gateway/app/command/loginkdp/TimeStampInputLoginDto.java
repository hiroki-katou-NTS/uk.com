/**
 * 
 */
package nts.uk.ctx.sys.gateway.app.command.loginkdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;

/**
 * @author laitv
 *
 */
@Data
@AllArgsConstructor
public class TimeStampInputLoginDto extends CheckChangePassDto{

	private Boolean result; //・Result (True/False)
	
	private EmployeeImport em; //・Employees
	
	private String errorMessage; //·Error message
}
