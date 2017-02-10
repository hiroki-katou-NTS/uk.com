package nts.uk.ctx.basic.app.query.organization.department;

import java.util.Date;

import lombok.Data;

@Data
public class DepartmentDto {

	private String companyCode;

	private String departmentCode;

	private String historyId;

	private Date endDate;

	private String externalCode;

	private String fullName;

	private String hierarchyCode;

	private String name;

	private Date startDate;

	public DepartmentDto(String departmentCode, String externalCode, String fullName, String hierarchyCode,
			String name) {
		this.departmentCode = departmentCode;
		this.externalCode = externalCode;
		this.fullName = fullName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
	}

}
