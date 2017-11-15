package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

@Stateless
public class EmployeeInfoPubImp implements EmployeeInfoPub {

	@Inject
	private EmployeeRepository repo;

	@Inject
	PersonRepository personRepo;

	@Override
	public Optional<EmployeeInfoDtoExport> getEmployeeInfo(String companyId, String employeeCode,
			GeneralDate entryDate) {
		// TODO Auto-generated method stub

		Optional<Employee> domain = repo.findByEmployeeCode(companyId, employeeCode, entryDate);

		if (!domain.isPresent()) {
			return Optional.empty();
		} else {
			Employee _domain = domain.get();
			return Optional.of(new EmployeeInfoDtoExport(_domain.getCompanyId(), _domain.getSCd().v(), _domain.getSId(),
					_domain.getPId()));
		}

	}

	@Override
	public List<EmployeeInfoDtoExport> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate standardDate) {

		List<Employee> listEmpDomain = repo.getListEmpByStandardDate(companyId, standardDate);

		List<EmployeeInfoDtoExport> result = new ArrayList<>();

		if (!listEmpDomain.isEmpty()) {
			listEmpDomain.forEach(c -> {
				EmployeeInfoDtoExport empDto = new EmployeeInfoDtoExport(c.getCompanyId(), c.getSCd().v(), c.getSId(),
						c.getPId());
				result.add(empDto);
			});
		}
		return result;
	}

	@Override
	public List<EmpBasicInfoExport> getListEmpBasicInfo(List<String> sid) {
		List<Employee> listEmpDomain = repo.getByListEmployeeId(sid);
		List<EmpBasicInfoExport> listResult = new ArrayList<>();

		if (!listEmpDomain.isEmpty()) {

			listResult = listEmpDomain.stream()
					.map(item -> EmpBasicInfoExport.builder()
							.employeeId(item.getSId())
							.employeeCode(item.getSCd().v())
							.pId(item.getPId())
							.companyMailAddress(item.getCompanyMail().v())
							.entryDate(item.getListEntryJobHist().get(0).getJoinDate())
							.retiredDate(item.getListEntryJobHist().get(0).getRetirementDate()).build())
					.collect(Collectors.toList());

			List<String> pids = listEmpDomain.stream().map(Employee::getPId).collect(Collectors.toList());

			List<Person> listPersonDomain = personRepo.getPersonByPersonIds(pids);

			if (!listPersonDomain.isEmpty()) {
				
				for (int i = 0; i < listPersonDomain.size(); i++) {
					for (int j = 0; j < listResult.size(); j++) {
						if (listPersonDomain.get(i).getPersonId() == listResult.get(j).getPId()) {
							listResult.get(j).setPersonMailAddress(listPersonDomain.get(i).getMailAddress().v());
							listResult.get(j).setPersonName(listPersonDomain.get(i).getPersonNameGroup().getPersonName().v());
							listResult.get(j).setGender(listPersonDomain.get(i).getGender().value);
							listResult.get(j).setBirthDay(listPersonDomain.get(i).getBirthDate());
						}
					}
				}

			}
		}

		return listResult;
	}

}
