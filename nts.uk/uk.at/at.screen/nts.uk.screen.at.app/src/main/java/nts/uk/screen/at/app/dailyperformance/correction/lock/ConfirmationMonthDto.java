package nts.uk.screen.at.app.dailyperformance.correction.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationMonthDto {
	
	private String companyID;
	
	private String employeeId;
	
	private int closureId;
	
	private int closureDay;
	
	private int processYM;
}
