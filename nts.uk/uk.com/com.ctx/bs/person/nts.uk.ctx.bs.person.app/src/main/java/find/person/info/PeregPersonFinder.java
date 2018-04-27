package find.person.info;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class PeregPersonFinder implements PeregFinder<PersonDto>{

	@Inject
	private PersonRepository personRepository;
	
	@Override
	public String targetCategoryCode() {
		return "CS00002";
	}

	@Override
	public Class<PersonDto> dtoClass() {
		return PersonDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.PERSON;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		
		Optional<Person> person = personRepository.getByPersonId(query.getPersonId());
		if(person.isPresent())
			return PersonDto.createFromDomain(person.get());
		return null;
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

}
