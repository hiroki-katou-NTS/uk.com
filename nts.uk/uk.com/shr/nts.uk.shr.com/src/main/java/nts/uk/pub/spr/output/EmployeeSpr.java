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
public class EmployeeSpr {
	/** The company id. */
	private String companyID;

	/** The employee code. */
	private String employeeCD;

	/** The employee id. */
	private String employeeID;

	/** The person Id. */
	private String personID;
	
	private String perName;
}
