package nts.uk.query.app.employee.ccg029;

import lombok.Data;
import nts.uk.ctx.pereg.dom.filemanagement.services.PersonFileManagementDto;
import nts.uk.query.model.department.DepartmentModel;
import nts.uk.query.model.employement.EmploymentModel;
import nts.uk.query.model.position.PositionModel;
import nts.uk.query.model.workplace.WorkplaceModel;

@Data
public class Ccg029EmployeeInforDto {

	private String personalId;
	
	private String employeeId;

	private String employeeCode;
	
	private String businessName;
	
	private String businessNameKana;
	
	private String workplaceId;

	private String workplaceCode;
	
	private String workplaceName;

	private String departmentId;
	
	private String departmentCode;
	
	private String departmentName;
	
	private String positionId;
	
	private String positionCode;
	
	private String positionName;

	private String employmentCode;

	private String employmentName;
	
	private String avartaFileId;
	
	private String mapFileId;
	
	public Ccg029EmployeeInforDto(String personalId, String employeeId, String employeeCode, String businessName, String businessNameKana) {
		super();
		this.personalId = personalId;
		this.employeeId = employeeId;
		this.employeeCode = employeeCode;
		this.businessName = businessName;
		this.businessNameKana = businessNameKana;
	}
	
	public void setWorkplace(WorkplaceModel workplace) {
		this.workplaceId = workplace.getWorkplaceId();
		this.workplaceCode = workplace.getWorkplaceCode();
		this.workplaceName = workplace.getWorkplaceName();
	}
	
	public void setDepartment(DepartmentModel department) {
		this.departmentCode = department.getDepartmentCode();
		this.departmentName = department.getDepartmentName();
	}
	
	public void setPosition(PositionModel position) {
		this.positionId = position.getPositionId();
		this.positionCode = position.getPositionCode();
		this.positionName = position.getPositionName();
	}
	
	public void setEmployment(EmploymentModel employment) {
		this.employmentCode = employment.getEmploymentCode();
		this.employmentName = employment.getEmploymentName();
	}
	
	public void setPersonalFileManagement(PersonFileManagementDto personFile) {
		this.avartaFileId = personFile.getAvatarFile().isPresent()?personFile.getAvatarFile().get().getThumbnailFileID():null;
		this.mapFileId = personFile.getMapFileID().isPresent()?personFile.getMapFileID().get():null;
	}

}
