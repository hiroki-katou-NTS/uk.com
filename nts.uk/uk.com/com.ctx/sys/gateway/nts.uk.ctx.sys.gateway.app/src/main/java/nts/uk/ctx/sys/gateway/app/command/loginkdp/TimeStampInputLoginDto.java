/**
 * 
 */
package nts.uk.ctx.sys.gateway.app.command.loginkdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.app.command.login.password.CheckChangePassDto;
import nts.uk.ctx.sys.shared.dom.employee.EmployeeImport;

/**
 * @author laitv
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TimeStampInputLoginDto extends CheckChangePassDto {
	private boolean result; // note: ・Result (True/False)

	private EmployeeImport em; // note: ・Employees

	private String errorMessage; // note: ·Error message
}
