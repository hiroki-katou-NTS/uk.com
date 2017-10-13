package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
@Data
@AllArgsConstructor
public class EmployeeUnregisterOutput {
	private EmployeeImport empInfor;
	
	private List<String> appType;
	
	
}
