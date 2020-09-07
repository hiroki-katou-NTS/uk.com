package nts.uk.ctx.at.record.app.command.kmp.kmp001.a;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeCardInformationCommand {

	private String employeeId;
	private String cardNumber;
	
}
