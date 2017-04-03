package nts.uk.ctx.basic.dom.organization.department;

import java.time.LocalDate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.company.CompanyCode;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class Department {

	private String companyCode;

	private DepartmentCode departmentCode;

	private String historyId;

	private GeneralDate endDate;

	private DepartmentCode externalCode;

	private DepartmentGenericName fullName;

	private HierarchyCode hierarchyCode;

	private DepartmentName departmentName;

	private GeneralDate startDate;

	public Department(String companyCode, DepartmentCode departmentCode, String historyId, GeneralDate endDate,
			DepartmentCode externalCode, DepartmentGenericName fullName, HierarchyCode hierarchyCode,
			DepartmentName name, GeneralDate startDate) {
		this.companyCode = companyCode;
		this.departmentCode = departmentCode;
		this.historyId = historyId;
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.fullName = fullName;
		this.hierarchyCode = hierarchyCode;
		this.departmentName = name;
		this.startDate = startDate;
	}

	public Department(String companyCode, DepartmentCode departmentCode, GeneralDate endDate,
			DepartmentCode externalCode, DepartmentGenericName fullName, HierarchyCode hierarchyCode,
			DepartmentName name, GeneralDate startDate) {
		this.companyCode = companyCode;
		this.departmentCode = departmentCode;
		this.historyId = IdentifierUtil.randomUniqueId();
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.fullName = fullName;
		this.hierarchyCode = hierarchyCode;
		this.departmentName = name;
		this.startDate = startDate;
	}

	
	public Department(String historyId, GeneralDate startDate, GeneralDate endDate) {
		super();
		this.historyId = historyId;
		this.endDate = endDate;
		this.startDate = startDate;
	}

	public static Department createSimpleFromJavaType(String startDate, String endDate, String historyId) {
		return new Department(historyId, GeneralDate.localDate(LocalDate.parse(startDate)),
				GeneralDate.localDate(LocalDate.parse(endDate)));
	}

}
