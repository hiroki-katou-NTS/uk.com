package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;
@Data
public class EmployeeUnregisterOutput {
	private EmployeeImport empInfor;
	
	private List<String> appType;
	
	
}
