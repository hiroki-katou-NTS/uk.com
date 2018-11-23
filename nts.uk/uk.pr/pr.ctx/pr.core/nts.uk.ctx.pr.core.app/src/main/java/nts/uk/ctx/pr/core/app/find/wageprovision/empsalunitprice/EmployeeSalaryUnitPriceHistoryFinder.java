package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeSalaryUnitPriceHistoryFinder {

    @Inject
    private EmployeeSalaryUnitPriceHistoryRepository repository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    public EmployeeSalaryUnitPriceDto getEmployeeSalaryUnitPriceDto(String personalUnitPriceCode, List<String> employeeIds) {
        return new EmployeeSalaryUnitPriceDto(this.repository.getEmployeeSalaryUnitPriceHistory(personalUnitPriceCode, employeeIds), this.employeeInfoAdapter.getByListSid(employeeIds));
    }

    public List<IndEmpSalUnitPriceHistoryDto> getAllIndividualEmpSalUnitPriceHistoryDto(IndEmpSalUnitPriceHistoryDto dto) {
        return repository.getAllIndividualEmpSalUnitPriceHistory(dto.getPersonalUnitPrice(), dto.getEmployeeId())
                .stream().map(IndEmpSalUnitPriceHistoryDto::fromDomainToDto)
                .collect(Collectors.toList());
    }
}
