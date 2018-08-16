package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
@Data
public class EmpRsvLeaveInforDto {
	/**
	 * employees
	 */
	private List<EmployeeInfoImport> employeeInfors;
	
	private RsvLeaManagerImport rsvLeaManaImport;
	
	private String employeeCode;
	
	private String employeeName;

}
