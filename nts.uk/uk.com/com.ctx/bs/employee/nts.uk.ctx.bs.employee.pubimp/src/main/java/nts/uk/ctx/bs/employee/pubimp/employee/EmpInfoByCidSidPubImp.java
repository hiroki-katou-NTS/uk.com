package nts.uk.ctx.bs.employee.pubimp.employee;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpInfoByCidSidExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmpInfoByCidSidPub;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

@Stateless
public class EmpInfoByCidSidPubImp implements EmpInfoByCidSidPub {

	@Inject
	private EmployeeRepository empRepo;

	@Inject
	private PersonRepository perRepo;

	@Override
	public EmpInfoByCidSidExport getEmpInfoBySidCid(String pid, String cid) {

		EmpInfoByCidSidExport result = new EmpInfoByCidSidExport();

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		GeneralDate systemDate = GeneralDate.legacyDate(new Date(sdf.format(date)));

		Optional<Employee> empOpt = empRepo.findBySidCidSystemDate(cid, pid, systemDate);

		if (empOpt.isPresent()) {
			Employee employee = empOpt.get();
			Person person = getPersonInfo(employee.getPId());
			setResult(employee, person);
		}

		return result;
	}

	/**
	 * @param person
	 * @param employee
	 */
	private EmpInfoByCidSidExport setResult(Employee employee, Person person) {
		EmpInfoByCidSidExport result = new EmpInfoByCidSidExport();
		result.setPid(employee.getSId());
		result.setCid(employee.getCompanyId());
		result.setScd(employee.getSCd().v());
		result.setSid(employee.getSId());
		result.setPersonName(person.getPersonNameGroup().getPersonName().getFullName().v());
		return result;
	}

	private Person getPersonInfo(String pid) {
		Optional<Person> personOpt = perRepo.getByPersonId(pid);
		Person person = new Person();
		if (personOpt.isPresent()) {
			person = personOpt.get();
		}
		return person;
	}

}
