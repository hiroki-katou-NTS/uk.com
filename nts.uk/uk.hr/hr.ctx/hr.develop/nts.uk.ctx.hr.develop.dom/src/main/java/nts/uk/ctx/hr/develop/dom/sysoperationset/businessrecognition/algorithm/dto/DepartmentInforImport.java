package nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepartmentInforImport {

	private String departmentId;
	
	private String hierarchyCode;
	
	private String departmentCode;
	
	private String departmentName;
	
	private String codeName;
	
	private String departmentDisplayName;
	
	private String departmentGenericName;
	
	private String departmentExternalCode;
	
	private List<DepartmentInforImport> children;

	public DepartmentInforImport(String departmentId, String hierarchyCode, String departmentCode,
			String departmentName, String departmentDisplayName, String departmentGenericName,
			String departmentExternalCode, List<DepartmentInforImport> children) {
		super();
		this.departmentId = departmentId;
		this.hierarchyCode = hierarchyCode;
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.codeName = departmentCode+ " " +departmentName;
		this.departmentDisplayName = departmentDisplayName;
		this.departmentGenericName = departmentGenericName;
		this.departmentExternalCode = departmentExternalCode;
		this.children = children;
	}

}