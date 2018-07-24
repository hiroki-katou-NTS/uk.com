package nts.uk.screen.at.app.dailyperformance.correction.searchemployee;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class DPEmployeeSearchData {

	/** The employee id. */
	private String employeeId;
	
	/** The employee code. */
	private String employeeCode;
	
	/** The business name. */
	private String businessName;
	
	/** The org name. */
	private String orgName;
}
