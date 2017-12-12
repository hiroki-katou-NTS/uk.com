package nts.uk.ctx.pereg.app.find.person.info;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDto;
import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

@Stateless
public class PersonFinder {

	/** The employee repository. */
	@Inject
	private EmployeeRepository empRepo;

	@Inject
	private PersonRepository personRepo;

	public PersonDto getPersonByEmpId(String employeeId) {
		Optional<Employee> emp = empRepo.getBySid(employeeId);

		if (emp.isPresent()) {
			Optional<Person> person = personRepo.getByPersonId(emp.get().getPId());

			if (person.isPresent()) {
				return PersonDto.fromDomain(person.get());
			}
		}

		return null;
	}

	public EmployeeToDeleteDto getPersonBypId(String pid) {

		Optional<Person> person = personRepo.getByPersonId(pid);

		if (person.isPresent()) {
			return EmployeeToDeleteDto.fromDomain(PersonDto.fromDomain(person.get()).getPersonNameGroup().getBusinessName());
		} else {
			return null;
		}
	}

}
