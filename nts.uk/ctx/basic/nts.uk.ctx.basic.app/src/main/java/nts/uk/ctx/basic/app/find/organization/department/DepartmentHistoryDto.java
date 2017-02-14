package nts.uk.ctx.basic.app.find.organization.department;

import java.util.Date;

import lombok.Data;

@Data
public class DepartmentHistoryDto {

	private Date startDate;

	private Date endDate;

	private String historyId;

	public DepartmentHistoryDto(String historyId, Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.historyId = historyId;
	}

}
