package nts.uk.screen.at.app.query.kmp.kmp001.b;

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
@AllArgsConstructor
@NoArgsConstructor
public class InformationEmployeeDtoViewB {

	private String businessName;

	private GeneralDate entryDate;

	/** The employee id. */
	private String employeeId;

	/** The employee code. */
	private String employeeCode;

	private GeneralDate retiredDate;
	
}
