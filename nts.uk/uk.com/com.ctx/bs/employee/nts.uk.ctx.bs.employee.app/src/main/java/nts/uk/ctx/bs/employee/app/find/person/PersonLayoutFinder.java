package nts.uk.ctx.bs.employee.app.find.person;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class PersonLayoutFinder implements PeregFinder<PersonLayoutDto, PeregQuery> {

	/** The employee repository. */
	@Inject
	private EmployeeRepository empRepo;

	@Inject
	private PersonRepository personRepo;

	@Inject
	private PerInfoItemDataRepository perInfoItemDataRepository;

	@Override
	public String targetCategoryCode() {
		return "CS00001";
	}

	@Override
	public Class<?> finderClass() {
		return PersonPeregDto.class;
	}

	/**
	 * the function handles finder return: PeregQueryResult
	 */
	@Override
	public PersonLayoutDto getData(PeregQuery query) {

		// get Dto: Employee -> Person
		Employee employee = empRepo.findBySid(AppContexts.user().companyId(), query.getEmpId()).get();
		if (employee == null)
			return new PersonLayoutDto();
		Optional<Person> person = personRepo.getByPersonId(employee.getPId());
		List<PersonInfoItemData> lstCtgItemOptionalDto = perInfoItemDataRepository
				.getAllInfoItemByRecordId(person.get().getPersonId());
		return new PersonLayoutDto(PersonPeregDto.createFromDomain(person.get()), lstCtgItemOptionalDto);
	}
}
