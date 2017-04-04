package nts.uk.ctx.basic.app.find.organization.workplace;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class WorkPlaceHistoryDto {

	private GeneralDate startDate;

	private GeneralDate endDate;

	private String historyId;

	public WorkPlaceHistoryDto(String historyId, GeneralDate startDate, GeneralDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.historyId = historyId;
	}

}
