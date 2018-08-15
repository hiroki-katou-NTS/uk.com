package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author HungTT
 *
 */

@Value
public class AggrPeriodErrorInfoDto {
	
	private String employeeId;
	private String employeeCode;
	private String employeeName;
	private GeneralDate procDate;
	private String errorMessage;
	
}
