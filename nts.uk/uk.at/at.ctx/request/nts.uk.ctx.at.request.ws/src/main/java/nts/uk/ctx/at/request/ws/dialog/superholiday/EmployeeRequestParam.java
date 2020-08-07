package nts.uk.ctx.at.request.ws.dialog.superholiday;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.app.find.dialog.employmentsystem.EmployeeBasicInfoDto;

@Value
public class EmployeeRequestParam {
	
	/** The employee basic info. */
	private List<EmployeeBasicInfoDto> employeeBasicInfo;
	
	/** The base date. */
	private String baseDate;
}
