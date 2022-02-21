package nts.uk.file.com.app;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.output.EmployeeUnregisterOutput;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.workplace.WorkplaceInfoImport;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeeUnregisterOutputDataSoure {
   private GeneralDate baseDate;
   private String companyName;
   private List<EmployeeUnregisterOutput> employeeUnregisterOutputLst;
   private List<EmployeeInformation> employeeInfors;
   private List<WorkplaceInfoImport> workplaceInfos;
}
