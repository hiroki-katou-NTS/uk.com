package nts.uk.ctx.bs.employee.app.find.person;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class PeregPersonFinder implements PeregFinder<PeregPersonDto> {

	@Inject
	private PersonRepository personRepo;

	@Override
	public String targetCategoryCode() {
		return "CS00001";
	}

	@Override
	public Class<PeregPersonDto> dtoClass() {
		return PeregPersonDto.class;
	}

	/**
	 * the function handles finder return: PeregQueryResult
	 */
	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<Person> person = personRepo.getByPersonId(query.getPersonId());
		return PeregPersonDto.createFromDomain(person.get());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.shr.pereg.app.find.PeregFinder#getListData(nts.uk.shr.pereg.app.
	 * find.PeregQuery)
	 */
	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.shr.pereg.app.find.PeregFinder#dataType()
	 */
	@Override
	public DataClassification dataType() {
		// TODO Auto-generated method stub
		return null;
	}
}
