package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;


import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.WorkIndividualPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.EmployeeInfoImport;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeSalaryUnitPriceDto {

    List<WorkIndividualPrice> workIndividualPrices;

    List<EmployeeInfoImport> employeeInfoImports;


}
