package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmployeeSalaryUnitPriceHistoryFinder {

    @Inject
    EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    public EmployeeSalaryUnitPriceHistoryDto getEmployeeSalaryUnitPriceHistoryDto(String personalUnitPriceCode, String employeeId) {
        return EmployeeSalaryUnitPriceHistoryDto.fromDomain(employeeSalaryUnitPriceHistoryRepository.getEmployeeSalaryUnitPriceHistory(personalUnitPriceCode, employeeId).orElse(null));
    }

}
