package nts.uk.screen.at.app.query.ksu.ksu002.a.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeInformationInput {

	public String employeeId;
	
	public GeneralDate baseDate;
	
}
