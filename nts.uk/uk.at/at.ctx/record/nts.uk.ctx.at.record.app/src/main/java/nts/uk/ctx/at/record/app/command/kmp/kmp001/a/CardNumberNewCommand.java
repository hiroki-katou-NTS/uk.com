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
@AllArgsConstructor
@NoArgsConstructor
public class CardNumberNewCommand {

	private String employeeId;
	private String cardNumber;
	
}
