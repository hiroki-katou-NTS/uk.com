package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import java.math.BigDecimal;
import java.util.List;

/**
* 社員給与単価履歴
*/
public interface EmployeeSalaryUnitPriceHistoryRepository {

    void updateAllHistory(String historyId, BigDecimal UnitPrice);

    List<WorkIndividualPrice> getEmployeeSalaryUnitPriceHistory(String personalUnitPriceCode, List<String> employeeId, int yearMonthFilter);

    void add(EmployeeSalaryUnitPriceHistory domain);

    void update(EmployeeSalaryUnitPriceHistory domain);

    void remove(String personalUnitPriceCode, String employeeId, String historyId);

    List<IndEmpSalUnitPriceHistory> getAllIndividualEmpSalUnitPriceHistory(String perUnitPriceCode, String employeeId);

    void updateAmount(PayrollInformation domain);

    void addHistory(EmployeeSalaryUnitPriceHistory domain1, PayrollInformation domain2);

    void updateHistory(EmployeeSalaryUnitPriceHistory domain);

    void deleteHistory(String historyId);

    void updateOldHistory(String historyId, int newEndYearMonth);

    List<IndEmpSalUnitPriceHistory> getIndividualUnitPriceList(String perUnitPriceCode, String employeeId, int baseYearMonth);
}
