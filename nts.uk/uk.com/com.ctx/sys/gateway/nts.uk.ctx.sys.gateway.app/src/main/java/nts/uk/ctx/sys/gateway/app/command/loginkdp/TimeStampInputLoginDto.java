/**
 * 
 */
package nts.uk.ctx.sys.gateway.app.command.loginkdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.app.command.login.dto.CheckChangePassDto;
import nts.uk.ctx.sys.gateway.dom.login.dto.EmployeeImport;

/**
 * @author laitv
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeStampInputLoginDto extends CheckChangePassDto{

	public boolean result; //・Result (True/False)
	
	public EmployeeImport em; //・Employees
	
	public String errorMessage; //·Error message
}
