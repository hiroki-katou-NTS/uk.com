package nts.uk.ctx.at.record.app.command.kmp.kmp001.c;

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
public class RegisterStampCardViewCCommand {

	private String employeeId;
	private String cardNumber;
	
}
