/**
 * 
 */
package nts.uk.ctx.sys.assist.app.command.deletedata.manualsetting;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author hiep.th
 *
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class EmployeesDeletionCommand {
	/**
	 * 社員ID
	 */
	private String employeeId;

	/**
	 * 
	 */
	private String employeeCode;

	/**
	 * ビジネスネーム
	 */
	private String businessName;
}
