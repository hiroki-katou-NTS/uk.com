package find.person.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.info.Person;
import nts.uk.ctx.bs.person.dom.person.info.PersonRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
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

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		// key - pid , value - sid getPersonId getEmployeeId
		Map<String, String> mapSids = query.getEmpInfos().stream().collect(Collectors.toMap(PeregEmpInfoQuery:: getPersonId, PeregEmpInfoQuery:: getEmployeeId));
		List<String> pids  = new ArrayList<String>(mapSids.keySet());
		List<Person> domains = personRepository.getFullPersonByPersonIds(pids);
		
		List<GridPeregDomainDto> result = domains.stream().map(c ->  {
			String sid = mapSids.get(c.getPersonId());
			return new GridPeregDomainDto (sid, c.getPersonId(), PersonDto.createFromDomain(c));
		}).collect(Collectors.toList());
		
		if(query.getEmpInfos().size() > result.size()) {
			for(int i  = result.size(); i < query.getEmpInfos().size() ; i++) {
				PeregEmpInfoQuery emp = query.getEmpInfos().get(i);
				result.add(new GridPeregDomainDto(emp.getEmployeeId(), emp.getPersonId(), null));
			}
		}
		
		return result;
	}
}
