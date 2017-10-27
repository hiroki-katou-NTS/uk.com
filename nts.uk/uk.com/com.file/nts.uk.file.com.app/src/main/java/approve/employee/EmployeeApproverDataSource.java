package approve.employee;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.WpApproverAsAppOutput;
import nts.uk.file.com.app.HeaderEmployeeUnregisterOutput;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeApproverDataSource {
	
	//header
	HeaderEmployeeUnregisterOutput headerEmployee;
	
	// workplace info: employees info and phrase of employee
	Map<String, WpApproverAsAppOutput> wpApprover;

}
