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
public class EmployeeCardInformationViewACommand {

	private String employeeId;
	private String olderCardNumber;
	private String newCardNumber;
	
}
