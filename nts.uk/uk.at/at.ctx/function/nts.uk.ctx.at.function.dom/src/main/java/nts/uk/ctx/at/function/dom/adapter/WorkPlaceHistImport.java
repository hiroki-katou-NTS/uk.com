package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceHistImport {
	
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	private List<WorkPlaceIdAndPeriodImport> lstWkpIdAndPeriod;

}
