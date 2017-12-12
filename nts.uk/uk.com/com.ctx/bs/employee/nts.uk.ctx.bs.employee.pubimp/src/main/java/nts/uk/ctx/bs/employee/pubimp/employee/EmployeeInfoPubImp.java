package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpBasicInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpInfoExport;
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

	@Inject
	AffCompanyHistRepository affCompanyHistRepo;

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
					.map(item -> EmpBasicInfoExport.builder().employeeId(item.getSId()).employeeCode(item.getSCd().v())
							.pId(item.getPId()).companyMailAddress(item.getCompanyMail().v())
							.entryDate(item.getListEntryJobHist().get(0).getJoinDate())
							.retiredDate(item.getListEntryJobHist().get(0).getRetirementDate()).build())
					.collect(Collectors.toList());

			List<String> pids = listEmpDomain.stream().map(Employee::getPId).collect(Collectors.toList());

			List<Person> listPersonDomain = personRepo.getPersonByPersonIds(pids);

			if (!listPersonDomain.isEmpty()) {
				for (int j = 0; j < listResult.size(); j++) {
					EmpBasicInfoExport resultItem = listResult.get(j);
					Person per = listPersonDomain.stream().filter(m -> m.getPersonId().equals(resultItem.getPId()))
							.collect(Collectors.toList()).get(0);
					listResult.get(j).setPersonMailAddress(null);
					listResult.get(j).setPersonName(per.getPersonNameGroup().getPersonName().getFullName() == null ? ""
							: per.getPersonNameGroup().getPersonName().getFullName().v());
					listResult.get(j).setGender(per.getGender() == null ? 0 : per.getGender().value);
					listResult.get(j).setBirthDay(per.getBirthDate());
				}
			}
		}

		return listResult;
	}

	/**
	 * Get Employee Info By Pid. Requets List No.124
	 */
	@Override
	public List<EmpInfoExport> getEmpInfoByPid(String pid) {

		List<EmpInfoExport> listResult = new ArrayList<>();

		if (pid == null) {
			return null;
		}
		// get domain Affiliated Company History-所属会社履歴
		AffCompanyHist affCompanyHist = this.affCompanyHistRepo.getAffCompanyHistoryOfPerson(pid);

		// get systemDate
		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(date);

		if (affCompanyHist != null) {

			if (!CollectionUtil.isEmpty(affCompanyHist.getLstAffCompanyHistByEmployee())) {

				// check all item in List<AffCompanyHistItem>
				for (AffCompanyHistByEmployee affCompanyHistByEmployee : affCompanyHist.getLstAffCompanyHistByEmployee()) {

					if (!CollectionUtil.isEmpty(affCompanyHistByEmployee.getLstAffCompanyHistoryItem())) {

						for (AffCompanyHistItem affCompanyHistItem : affCompanyHistByEmployee
								.getLstAffCompanyHistoryItem()) {
							
							if (systemDate.beforeOrEquals(affCompanyHistItem.end())
									&& systemDate.afterOrEquals(affCompanyHistItem.start())) {
								Optional<Person> personOpt = personRepo.getByPersonId(affCompanyHist.getPId());
								if (personOpt.isPresent()) {
									Person person = personOpt.get();
									EmpInfoExport empInfoExport = new EmpInfoExport();
									empInfoExport.setPId(person.getPersonId() == null ? "" : null);
									empInfoExport.setPersonName(person.getPersonNameGroup().getPersonName().toString() == null ? "" : null);
									empInfoExport.setEmployeeId(affCompanyHistByEmployee.getSId() == null ? "" : null);
									if (affCompanyHistByEmployee.getSId() != null) {
										Optional<Employee> employeeOpt = this.repo.getBySid(affCompanyHistByEmployee.getSId());
										if (employeeOpt.isPresent()) {
											Employee employee = employeeOpt.get();
											empInfoExport.setEmployeeCode(employee.getSCd() == null ? "" : employee.getSCd().v());
											empInfoExport.setCompanyId(employee.getCompanyId());
										}
									}
									listResult.add(empInfoExport);
								}
							}
						}
					}
				}
			}
		}
		return listResult;
	}
}
