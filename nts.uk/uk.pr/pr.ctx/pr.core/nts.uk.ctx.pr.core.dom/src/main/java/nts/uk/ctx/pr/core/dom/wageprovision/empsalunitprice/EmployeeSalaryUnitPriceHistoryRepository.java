package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import java.util.Optional;
import java.util.List;

/**
* 社員給与単価履歴
*/
public interface EmployeeSalaryUnitPriceHistoryRepository
{

    Optional<EmployeeSalaryUnitPriceHistory> getEmployeeSalaryUnitPriceHistory(String personalUnitPriceCode,List<String> employeeId);

    void add(EmployeeSalaryUnitPriceHistory domain);

    void update(EmployeeSalaryUnitPriceHistory domain);

    void remove(String personalUnitPriceCode, String employeeId, String historyId);

}
