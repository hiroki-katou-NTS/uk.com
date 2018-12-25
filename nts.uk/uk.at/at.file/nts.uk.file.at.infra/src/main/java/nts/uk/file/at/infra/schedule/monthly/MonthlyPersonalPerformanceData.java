/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.at.infra.schedule.monthly;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.file.at.app.export.dailyschedule.ActualValue;

/**
 * Instantiates a new daily personal performance data.
 * @author HoangNDH
 */
@Data
@NoArgsConstructor
public class MonthlyPersonalPerformanceData {
	
	/** The error alarm code. */
	public String errorAlarmCode;
	
	/** The employee name. */
	public String employeeName;
	
	/** The employee code. */
	public String employeeCode;
	
	/** The detailed error data. */
	public String detailedErrorData;
	
	/** The closure date. */
	public String closureDate;
	
	/** The actual value. */
	public List<ActualValue> actualValue;
}
