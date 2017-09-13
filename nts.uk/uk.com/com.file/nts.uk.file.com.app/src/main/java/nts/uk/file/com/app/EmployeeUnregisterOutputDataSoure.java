package nts.uk.file.com.app;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;

@Value
public class EmployeeUnregisterOutputDataSoure {
   List<EmployeeUnregisterOutput> employeeUnregisterOutputLst;
}
