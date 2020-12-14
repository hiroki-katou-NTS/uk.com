package nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfEmployeeRepository;

@Stateless
public class BusinessTypeOfEmployeeService {

	@Inject
	private BusinessTypeOfEmployeeRepository businessTypeOfEmpHisRepo;
	
	@Inject
	private BusinessTypeEmpOfHistoryRepository businessTypeEmpHiRepo;

	public List<BusinessTypeOfEmployeeHis> find(List<String> empIds, DatePeriod period) {
		
		List<BusinessTypeOfEmployee> listBusinessTypeOfEmpDto = businessTypeOfEmpHisRepo.findAllByEmpAndDate(empIds, period);
		
		return listBusinessTypeOfEmpDto.stream().map(c -> new BusinessTypeOfEmployeeHis(c, 
													businessTypeEmpHiRepo.findByHistoryId(c.getHistoryId()).get().getHistory().get(0)))
								.collect(Collectors.toList());
	}
}
