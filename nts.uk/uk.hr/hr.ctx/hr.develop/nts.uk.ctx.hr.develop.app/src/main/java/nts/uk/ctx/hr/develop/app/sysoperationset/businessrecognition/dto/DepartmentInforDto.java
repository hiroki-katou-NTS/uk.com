package nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.hr.develop.dom.sysoperationset.businessrecognition.algorithm.dto.DepartmentInforImport;

@AllArgsConstructor
@Getter
public class DepartmentInforDto {

	private List<DepartmentInforImport> departmentInforList;
	
	private String departmentIdSelect;
}
