package nts.uk.ctx.bs.employee.app.find.empfilemanagement.dto;

import lombok.Data;

@Data
public class EmployeeFileManagementDto {
	/**employee id*/
	private String employeeId;
	/**file id*/
	private String fileId;
	/**file type*/
	private int fileType;
	/** order document file*/
	private Integer uploadOrder;

	/** The PersonalInformationCtgID */
	private String personInfoCategoryId;
	
	public EmployeeFileManagementDto(String sId, String fileId, int fileType, Integer uploadOder, String perInfCtgId){
		this.employeeId = sId;
		this.fileId = fileId;
		this.fileType = fileType;
		this.uploadOrder = uploadOder;
		this.personInfoCategoryId = perInfCtgId;
	}
	public EmployeeFileManagementDto(String sId, String fileId, int fileType){
		this.employeeId = sId;
		this.fileId = fileId;
		this.fileType = fileType;
		this.uploadOrder = null;
		this.personInfoCategoryId = null;
	}
}
