package nts.uk.ctx.bs.employee.app.find.wkpdep;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;

/**
 *
 * @author HungTT
 *
 */
@Value
@AllArgsConstructor
public class InformationDto {

	private String id;
	private String code;
	private String name;
	private String dispName;
	private String genericName;
	private String hierarchyCode;
	private String externalCode;

	public InformationDto(WorkplaceInformation wkpInfor) {
		this.id = wkpInfor.getWorkplaceId();
		this.code = wkpInfor.getWorkplaceCode().v();
		this.name = wkpInfor.getWorkplaceName().v();
		this.dispName = wkpInfor.getWorkplaceDisplayName().v();
		this.genericName = wkpInfor.getWorkplaceGeneric().v();
		this.hierarchyCode = wkpInfor.getHierarchyCode().v();
		if (wkpInfor.getWorkplaceExternalCode().isPresent())
			this.externalCode = wkpInfor.getWorkplaceExternalCode().get().v();
		else
			this.externalCode = null;
	}

	public InformationDto(DepartmentInformation depInfor) {
		this.id = depInfor.getDepartmentId();
		this.code = depInfor.getDepartmentCode().v();
		this.name = depInfor.getDepartmentName().v();
		this.dispName = depInfor.getDepartmentDisplayName().v();
		this.genericName = depInfor.getDepartmentGeneric().v();
		this.hierarchyCode = depInfor.getHierarchyCode().v();
		if (depInfor.getDepartmentExternalCode().isPresent())
			this.externalCode = depInfor.getDepartmentExternalCode().get().v();
		else
			this.externalCode = null;
	}

}
