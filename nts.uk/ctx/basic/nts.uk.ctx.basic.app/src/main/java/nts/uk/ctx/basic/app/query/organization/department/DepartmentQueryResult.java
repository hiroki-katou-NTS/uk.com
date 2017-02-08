package nts.uk.ctx.basic.app.query.organization.department;

import java.util.List;

import lombok.Data;

@Data
public class DepartmentQueryResult {

	private List<DepartmentHistoryDto> histories;
	
	private List<DepartmentDto> departments;
	
	private DepartmentMemoDto memo;

}
