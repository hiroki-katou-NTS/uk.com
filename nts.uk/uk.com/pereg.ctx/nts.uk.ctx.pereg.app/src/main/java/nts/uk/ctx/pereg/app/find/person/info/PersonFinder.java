package nts.uk.ctx.pereg.app.find.person.info;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.employee.EmployeeToDeleteDto;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;

@Stateless
public class PersonFinder {

	/** The employee repository. */
	@Inject
	private EmployeeDataMngInfoRepository empRepo;

	@Inject
	private PersonRepository personRepo;

	public PersonDto getPersonByEmpId(String employeeId) {
		Optional<EmployeeDataMngInfo> emp = empRepo.findByEmpId(employeeId);

		if (emp.isPresent()) {
			Optional<Person> person = personRepo.getByPersonId(emp.get().getPersonId());

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
