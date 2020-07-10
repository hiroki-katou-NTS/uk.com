package nts.uk.ctx.at.record.app.command.kmp.kmp001.b;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeCardCommand {
	private String employeeId;
	private String cardNumber;
	private GeneralDate dateRegister;
}
