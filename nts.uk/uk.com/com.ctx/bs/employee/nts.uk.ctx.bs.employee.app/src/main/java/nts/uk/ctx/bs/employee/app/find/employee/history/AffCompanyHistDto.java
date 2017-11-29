package nts.uk.ctx.bs.employee.app.find.employee.history;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class AffCompanyHistDto {
	private String personId;
	private String employeeId;
	private String historyId;

	private boolean destinationData;
	private GeneralDate startDate;
	private GeneralDate endDate;
}
