package nts.uk.ctx.basic.dom.organization.workplace;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyCode;
import nts.uk.ctx.basic.dom.organization.shr.HierarchyLevelCd;

@Getter
public class WorkPlace {

	private final String companyCode;

	private final WorkPlaceCode workPlaceCode;

	private final String historyId;

	private GeneralDate endDate;

	private WorkPlaceCode externalCode;

	private WorkPlaceGenericName genericName;

	private HierarchyCode hierarchyCode;

	private HierarchyLevelCd hierarchyLevelCd;

	private WorkPlaceName name;

	private ParentChildAttribute parentChildAttribute1;

	private ParentChildAttribute parentChildAttribute2;

	private WorkPlaceCode parentWorkCode1;

	private WorkPlaceCode parentWorkCode2;

	private WorkPlaceShortName shortName;

	private GeneralDate startDate;

	public WorkPlace(String companyCode, WorkPlaceCode workPlaceCode, String historyId, GeneralDate endDate,
			WorkPlaceCode externalCode, WorkPlaceGenericName genericName, HierarchyCode hierarchyCode,
			HierarchyLevelCd hierarchyLevelCd, WorkPlaceName name, ParentChildAttribute parentChildAttribute1,
			ParentChildAttribute parentChildAttribute2, WorkPlaceCode parentWorkCode1, WorkPlaceCode parentWorkCode2,
			WorkPlaceShortName shortName, GeneralDate startDate) {
		this.companyCode = companyCode;
		this.workPlaceCode = workPlaceCode;
		this.historyId = historyId;
		this.endDate = endDate;
		this.externalCode = externalCode;
		this.genericName = genericName;
		this.hierarchyCode = hierarchyCode;
		this.hierarchyLevelCd = hierarchyLevelCd;
		this.name = name;
		this.parentChildAttribute1 = parentChildAttribute1;
		this.parentChildAttribute2 = parentChildAttribute2;
		this.parentWorkCode1 = parentWorkCode1;
		this.parentWorkCode2 = parentWorkCode2;
		this.shortName = shortName;
		this.startDate = startDate;
	}

}
