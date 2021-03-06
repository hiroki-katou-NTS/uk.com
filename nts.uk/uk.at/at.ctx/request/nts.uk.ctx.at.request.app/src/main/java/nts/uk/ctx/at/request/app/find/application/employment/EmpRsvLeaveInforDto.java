package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;

@Getter
@AllArgsConstructor
public class EmpRsvLeaveInforDto {
	/**
	 * employees
	 */
	private List<EmployeeInfoImport> employeeInfors;
	
	private RsvLeaManagerImportDto rsvLeaManaImport;
	
	private String employeeCode;
	
	private String employeeName;
	
	//積立年休名称
	private String yearResigName;
	
	// 積立年休管理区分
	private boolean retentionManage;
}
