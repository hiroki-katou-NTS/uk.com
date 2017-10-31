package nts.uk.file.com.app;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeUnregisterOutputDataSoure {
	
   HeaderEmployeeUnregisterOutput headerEmployee;
   List<EmployeeUnregisterOutput> employeeUnregisterOutputLst;
}
