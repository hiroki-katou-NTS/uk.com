package nts.uk.ctx.basic.dom.organization.department;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyLevelCd;

@Getter
public class Department {

	private final String companyCode;

	private final DepartmentCode departmentCode;

	private final String historyId;

	private GeneralDate endDate;

	private DepartmentCode externalCode;

	private DepartmentGenericName genericName;

	private HierarchyCode hierarchyCode;

	private DepartmentName name;

	private DepartmentShortName shortName;

	private GeneralDate startDate;

	private HierarchyLevelCd hierarchyLevelCd;

	public Department(String companyCode, DepartmentCode departmentCode, String historyId, GeneralDate endDate,
			DepartmentCode externalCode, DepartmentGenericName genericName, HierarchyCode hierarchyCode,
			DepartmentName name, DepartmentShortName shortName, GeneralDate startDate,
			HierarchyLevelCd hierarchyLevelCd) {
		this.companyCode = companyCode;
		this.departmentCode = departmentCode;
		this.historyId = historyId;
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.genericName = genericName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.shortName = shortName;
		this.startDate = startDate;
		this.hierarchyLevelCd = hierarchyLevelCd;
	}

}
