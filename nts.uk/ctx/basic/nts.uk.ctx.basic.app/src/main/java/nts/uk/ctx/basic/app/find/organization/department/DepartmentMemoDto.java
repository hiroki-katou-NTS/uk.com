package nts.uk.ctx.basic.app.find.organization.department;

import lombok.Data;

@Data
public class DepartmentMemoDto {

	private String companyCode;

	private String historyId;

	private String memo;

	public DepartmentMemoDto(String historyId, String memo) {
		this.historyId = historyId;
		this.memo = memo;
	}

}
