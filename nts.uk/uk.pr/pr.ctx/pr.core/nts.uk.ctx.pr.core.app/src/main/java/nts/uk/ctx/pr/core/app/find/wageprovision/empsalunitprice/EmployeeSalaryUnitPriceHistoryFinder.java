package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.SalaryPerUnitPriceNameDto;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.IndEmpSalUnitPriceHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeSalaryUnitPriceHistoryFinder {

    @Inject
    private EmployeeSalaryUnitPriceHistoryRepository employeeSalaryUnitPriceHistoryRepository;

    @Inject
    private EmployeeInfoAdapter employeeInfoAdapter;

    @Inject
    private SalaryPerUnitPriceRepository salaryPerUnitPriceRepository;

    public EmployeeSalaryUnitPriceDto getEmployeeSalaryUnitPriceDto(String personalUnitPriceCode, List<String> employeeIds, int yearMonthFilter) {
        return new EmployeeSalaryUnitPriceDto(this.employeeSalaryUnitPriceHistoryRepository.getEmployeeSalaryUnitPriceHistory(personalUnitPriceCode, employeeIds, yearMonthFilter), this.employeeInfoAdapter.getByListSid(employeeIds));
    }

    public List<IndEmpSalUnitPriceHistoryDto> getAllIndividualEmpSalUnitPriceHistoryDto(IndEmpSalUnitPriceHistoryDto dto) {
        return employeeSalaryUnitPriceHistoryRepository.getAllIndividualEmpSalUnitPriceHistory(dto.getPersonalUnitPriceCode(), dto.getEmployeeId())
                .stream().map(IndEmpSalUnitPriceHistoryDto::fromDomainToDto)
                .collect(Collectors.toList());
    }

    public List<IndividualUnitPriceDto> getIndividualUnitPriceList(String employeeId, int baseYearMonth) {
        List<SalaryPerUnitPriceNameDto> salaryPerUnitPriceNameDtos = salaryPerUnitPriceRepository.getAllSalaryPerUnitPrice().stream().map(item -> SalaryPerUnitPriceNameDto.fromDomain(item.getSalaryPerUnitPriceName()))
                .collect(Collectors.toList());
        List<IndividualUnitPriceDto> individualUnitPriceDtos = new ArrayList<>();
        salaryPerUnitPriceNameDtos.forEach(v -> {
            List<IndEmpSalUnitPriceHistory> indEmpSalUnitPriceHistories = employeeSalaryUnitPriceHistoryRepository.getIndividualUnitPriceList(v.getCode(), employeeId, baseYearMonth);
            if(indEmpSalUnitPriceHistories.size() > 0) {
                individualUnitPriceDtos.add(new IndividualUnitPriceDto(
                        employeeId,
                        v.getCode(),
                        v.getName(),
                        0,
                        indEmpSalUnitPriceHistories.get(0).getStartYearMonth(),
                        indEmpSalUnitPriceHistories.get(0).getEndYearMonth(),
                        indEmpSalUnitPriceHistories.get(0).getAmountOfMoney()));
            }
        });
        return individualUnitPriceDtos;
    }
}
