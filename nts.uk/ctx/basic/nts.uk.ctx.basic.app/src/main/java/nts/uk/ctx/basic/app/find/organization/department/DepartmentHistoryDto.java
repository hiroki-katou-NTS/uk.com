package nts.uk.ctx.basic.app.find.organization.department;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class DepartmentHistoryDto {

	private GeneralDate startDate;

	private GeneralDate endDate;

	private String historyId;

	public DepartmentHistoryDto(String historyId, GeneralDate startDate, GeneralDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.historyId = historyId;
	}

}