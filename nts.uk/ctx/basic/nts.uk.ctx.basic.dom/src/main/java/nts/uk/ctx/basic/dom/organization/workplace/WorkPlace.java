package nts.uk.ctx.basic.dom.organization.workplace;


import java.time.LocalDate;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;

@Getter
public class WorkPlace {

	private String companyCode;

	private WorkPlaceCode workPlaceCode;

	private String historyId;

	private GeneralDate endDate;

	private WorkPlaceCode externalCode;
	
	private WorkPlaceGenericName genericName;

	private HierarchyCode hierarchyCode;

	private WorkPlaceName name;

	private ParentChildAttribute parentChildAttribute1;

	private ParentChildAttribute parentChildAttribute2;

	private WorkPlaceCode parentWorkCode1;

	private WorkPlaceCode parentWorkCode2;

	private WorkPlaceShortName shortName;

	private GeneralDate startDate;

	public WorkPlace(String companyCode, WorkPlaceCode workPlaceCode, String historyId, GeneralDate endDate,
			WorkPlaceCode externalCode, WorkPlaceGenericName genericName, HierarchyCode hierarchyCode, WorkPlaceName name,
			ParentChildAttribute parentChildAttribute1, ParentChildAttribute parentChildAttribute2,
			WorkPlaceCode parentWorkCode1, WorkPlaceCode parentWorkCode2, WorkPlaceShortName shortName,
			GeneralDate startDate) {
		this.companyCode = companyCode;
		this.workPlaceCode = workPlaceCode;
		this.historyId = historyId;
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.genericName = genericName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.parentChildAttribute1 = parentChildAttribute1;
		this.parentChildAttribute2 = parentChildAttribute2;
		this.parentWorkCode1 = parentWorkCode1;
		this.parentWorkCode2 = parentWorkCode2;
		this.shortName = shortName;
		this.startDate = startDate;
	}
	
	public WorkPlace(String companyCode, WorkPlaceCode workPlaceCode, GeneralDate endDate,
			WorkPlaceCode externalCode, WorkPlaceGenericName genericName, HierarchyCode hierarchyCode, WorkPlaceName name,
			ParentChildAttribute parentChildAttribute1, ParentChildAttribute parentChildAttribute2,
			WorkPlaceCode parentWorkCode1, WorkPlaceCode parentWorkCode2, WorkPlaceShortName shortName,
			GeneralDate startDate) {
		this.companyCode = companyCode;
		this.workPlaceCode = workPlaceCode;
		this.historyId = IdentifierUtil.randomUniqueId();
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.genericName = genericName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.parentChildAttribute1 = parentChildAttribute1;
		this.parentChildAttribute2 = parentChildAttribute2;
		this.parentWorkCode1 = parentWorkCode1;
		this.parentWorkCode2 = parentWorkCode2;
		this.shortName = shortName;
		this.startDate = startDate;
	}

	public WorkPlace(String companyCode, WorkPlaceCode workPlaceCode, GeneralDate endDate, WorkPlaceCode externalCode,
			WorkPlaceGenericName genericName, HierarchyCode hierarchyCode, WorkPlaceName name, WorkPlaceShortName shortName,
			GeneralDate startDate) {
		this.companyCode = companyCode;
		this.workPlaceCode = workPlaceCode;
		this.historyId = IdentifierUtil.randomUniqueId();
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.genericName = genericName;
		this.hierarchyCode = hierarchyCode;
		this.name = name;
		this.shortName = shortName;
		this.startDate = startDate;
	}

	public WorkPlace(String historyId, GeneralDate startDate, GeneralDate endDate) {
		this.historyId = historyId;
		this.endDate = endDate;
		this.startDate = startDate;
	}
	
	public static WorkPlace createSimpleFromJavaType(String startDate, String endDate, String historyId) {
		return new WorkPlace(historyId, GeneralDate.localDate(LocalDate.parse(startDate)),
				GeneralDate.localDate(LocalDate.parse(endDate)));
	}

}
