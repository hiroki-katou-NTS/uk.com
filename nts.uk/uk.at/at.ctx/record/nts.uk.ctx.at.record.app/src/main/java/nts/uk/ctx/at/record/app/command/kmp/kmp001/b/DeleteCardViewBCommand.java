package nts.uk.ctx.at.record.app.command.kmp.kmp001.b;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCardViewBCommand {
	
	private String employeeId;
	private String cardNumber;
	
}
