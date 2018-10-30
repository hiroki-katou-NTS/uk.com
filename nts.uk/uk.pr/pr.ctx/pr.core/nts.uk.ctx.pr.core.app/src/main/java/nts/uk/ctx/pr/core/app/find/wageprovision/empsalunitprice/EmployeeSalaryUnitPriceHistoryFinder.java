package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.EmployeeInfoAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class EmployeeSalaryUnitPriceHistoryFinder {

    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    public EmployeeSalaryUnitPriceDto getEmployeeSalaryUnitPriceDto(String personalUnitPriceCode, List<String> employeeIds) {
        return new EmployeeSalaryUnitPriceDto(this.employeeSalaryUnitPriceHistoryRepository.getEmployeeSalaryUnitPriceHistory(personalUnitPriceCode, employeeIds), this.employeeInfoAdapter.getByListSid(employeeIds));
    }

}
