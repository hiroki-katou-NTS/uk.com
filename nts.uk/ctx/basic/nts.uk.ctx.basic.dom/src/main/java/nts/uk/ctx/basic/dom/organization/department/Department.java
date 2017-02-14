package nts.uk.ctx.basic.dom.organization.department;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;

@Getter
public class Department {

	private String companyCode;

	private DepartmentCode departmentCode;

	private String historyId;

	private GeneralDate endDate;

	private DepartmentCode externalCode;

	private DepartmentFullName fullName;

	private HierarchyCode hierarchyCode;

	private DepartmentName name;

	private GeneralDate startDate;

	public Department(String companyCode, DepartmentCode departmentCode, String historyId, GeneralDate endDate,
			DepartmentCode externalCode, DepartmentFullName fullName, HierarchyCode hierarchyCode, DepartmentName name,
			GeneralDate startDate) {
		this.companyCode = companyCode;
		this.departmentCode = departmentCode;
		this.historyId = historyId;
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.fullName = fullName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.startDate = startDate;
	}

	public Department(String companyCode, DepartmentCode departmentCode, GeneralDate endDate,
			DepartmentCode externalCode, DepartmentFullName fullName, HierarchyCode hierarchyCode, DepartmentName name,
			GeneralDate startDate) {
		this.companyCode = companyCode;
		this.departmentCode = departmentCode;
		this.historyId = IdentifierUtil.randomUniqueId();
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.fullName = fullName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.startDate = startDate;
	}

	public Department(String historyId, GeneralDate endDate, GeneralDate startDate) {
		this.historyId = historyId;
		this.endDate = endDate;
		this.startDate = startDate;
	}

}
