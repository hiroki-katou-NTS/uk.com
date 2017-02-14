package nts.uk.ctx.basic.app.find.organization.workplace;

import java.util.Date;

import lombok.Data;

@Data
public class WorkPlaceHistoryDto {

	private Date startDate;

	private Date endDate;

	private String historyId;

	public WorkPlaceHistoryDto(String historyId, Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.historyId = historyId;
	}

}
