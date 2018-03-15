package nts.uk.pub.spr.output;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class EmployeeInfoSpr {
	/** The company id. */
	private String companyId;

	/** The employee code. */
	private String employeeCode;

	/** The employee id. */
	private String employeeId;

	/** The person Id. */
	private String personId;
	
	private String perName;
}
