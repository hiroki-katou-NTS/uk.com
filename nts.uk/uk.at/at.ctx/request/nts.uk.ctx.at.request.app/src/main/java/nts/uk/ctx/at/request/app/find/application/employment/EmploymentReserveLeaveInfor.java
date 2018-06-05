package nts.uk.ctx.at.request.app.find.application.employment;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.reserveleavemaneger.ReserveLeaveManagerImport;
@Data
public class EmploymentReserveLeaveInfor {
	/**
	 * employees
	 */
	private List<EmployeeInfoImport> employeeInfors;
	
	private ReserveLeaveManagerImport reserveLeaveManagerImport;
	
	private String employeeCode;
	
	private String employeeName;

}
