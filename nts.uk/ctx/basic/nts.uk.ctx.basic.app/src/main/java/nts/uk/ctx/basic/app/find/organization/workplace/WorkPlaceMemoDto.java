package nts.uk.ctx.basic.app.find.organization.workplace;

import lombok.Data;

@Data
public class WorkPlaceMemoDto {

	private String companyCode;

	private String historyId;

	private String memo;

	public WorkPlaceMemoDto(String historyId, String memo) {
		this.historyId = historyId;
		this.memo = memo;
	}

}
