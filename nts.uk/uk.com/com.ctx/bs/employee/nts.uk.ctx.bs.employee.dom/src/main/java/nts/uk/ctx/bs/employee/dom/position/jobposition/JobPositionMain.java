package nts.uk.ctx.bs.employee.dom.position.jobposition;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JobPositionMain extends AggregateRoot {

	/** The employeeId 社員ID */
	private String sId;

	/** The jpbPositionId */
	private String jobPositionId;

	/** The JobTitleID */
	private String jobTitleID;

	/** The Period Position */
	private PeriodPosition periodPosition;

	public static JobPositionMain createFromJavaType(
			String sId, 
			String jobPositionId, 
			String jobTitleID,
			PeriodPosition
			periodPosition) {
		return new JobPositionMain(sId, jobPositionId, jobTitleID, periodPosition);
	}

}
