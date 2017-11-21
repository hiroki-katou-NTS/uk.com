package nts.uk.ctx.bs.employee.app.find.person;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.Employee;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.i18n.resource.I18NResourcesForUK;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;

@Stateless
public class PersonFinderProcessor implements PeregFinder<Object, PeregQuery>{
	
	@Inject 
	private EmployeeRepository employeeRepository;
	
	@Inject
	private PersonRepository personRepository;
	
	@Inject
	I18NResourcesForUK ukResouce;

	@Override
	public String targetCategoryCode() {
		return "CS00001";
	}

	@Override
	public Class<?> finderClass() {
		return PersonLayoutDto.class;
	}

	/**
	 * the function handles finder
	 * return: PeregQueryResult
	 */
	@Override
	public Object getData(PeregQuery query) {
				
		// get Dto: Employee -> Person
		Employee employee = employeeRepository.findBySid(AppContexts.user().companyId(), query.getEmpId()).get();
		if(employee == null) return new Object();
		Optional<Person> person = personRepository.getByPersonId(employee.getPId());
		
		return PersonLayoutDto.createFromDomain(person.get());
	}

}
