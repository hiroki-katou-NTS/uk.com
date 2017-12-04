package nts.uk.ctx.bs.employee.pubimp.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.JobEntryHistoryExport;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

@Stateless
public class PersonInfoPubImp implements IPersonInfoPub {

	@Inject
	private EmployeeRepository empRepo;

	@Inject
	private PersonRepository personRepo;

	@Override
	public PersonInfoExport getPersonInfo(String employeeId) {
		Optional<Employee> employeeOpt = empRepo.getBySid(employeeId);
		PersonInfoExport perResult = null;
		
		if (employeeOpt.isPresent()) {
			Employee employee = employeeOpt.get();
			perResult = new PersonInfoExport();
			
			setEmployeeInfo(employee, perResult);
			
			setPersonInfo(employee.getPId(), perResult);
			
		}
		return perResult;
	}

	private void setPersonInfo(String pId, PersonInfoExport perResult) {
		Optional<Person> _person = personRepo.getByPersonId(pId);
		if (_person.isPresent()) {
			Person person = _person.get();
			String businessName = "";
			if (person.getPersonNameGroup() != null) {
				if (person.getPersonNameGroup().getBusinessName() != null) {
					businessName = person.getPersonNameGroup().getBusinessName().v();
				} else if (person.getPersonNameGroup().getPersonName() != null) {
					businessName = person.getPersonNameGroup().getPersonName().getFullName().v();
				}
			}
			perResult.setEmployeeName(businessName);
		}
	}

	private void setEmployeeInfo(Employee employee, PersonInfoExport perResult) {
		perResult.setEmployeeId(employee.getSId());
		if (employee.getSCd() != null) {
			perResult.setEmployeeCode(employee.getSCd().v());
		}
		if (employee.getCompanyMail() != null) {
			perResult.setCompanyMail(employee.getCompanyMail().v());
		}
		
		if (employee.getListEntryJobHist() != null) {
			List<JobEntryHistoryExport> listJobEntryHist = new ArrayList<>();
			employee.getListEntryJobHist().forEach(c -> {
				listJobEntryHist.add(new JobEntryHistoryExport(c.getCompanyId(), c.getSId(), c.getHiringType().v(),
						c.getRetirementDate(), c.getJoinDate(), c.getAdoptDate()));
			});
			perResult.setListJobEntryHist(listJobEntryHist);
		}
	}

}
