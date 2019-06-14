package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.EmployeeInfoImport;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.PersonalAmount;

import java.util.List;

@Value
@Data
@AllArgsConstructor
public class EmployeeInfoImportDto {
    List<EmployeeInfoImport> employeeInfoImports;
    List<PersonalAmount> personalAmount;
}
