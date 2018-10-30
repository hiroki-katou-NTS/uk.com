package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class EmployeeSalaryUnitPriceHistoryCommand {
    String personalUnitPriceCode;

    List<String> employeeIds;

}
