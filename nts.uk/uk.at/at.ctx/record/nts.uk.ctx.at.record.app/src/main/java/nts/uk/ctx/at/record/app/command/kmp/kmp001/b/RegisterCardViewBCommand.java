package nts.uk.ctx.at.record.app.command.kmp.kmp001.b;

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
public class RegisterCardViewBCommand {
	private String employeeId;
	private String employeeIdSelect;
	private String cardNumber;
}
