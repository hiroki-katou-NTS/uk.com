package nts.uk.ctx.at.record.dom.workrecord.erroralarm.mastercheck.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceHistImportAl {
	/** The employee id. */
	// 社員ID
	private String employeeId;
	
	private List<WorkPlaceIdAndPeriodImportAl> lstWkpIdAndPeriod;
}
