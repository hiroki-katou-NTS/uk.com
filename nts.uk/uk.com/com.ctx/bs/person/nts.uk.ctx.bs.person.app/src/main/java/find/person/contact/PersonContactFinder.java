package find.person.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class PersonContactFinder implements PeregFinder<PersonContactDto>{

	@Inject
	private PersonContactRepository perContactRepo;
	
	@Override
	public String targetCategoryCode() {
		return "CS00022";
	}

	@Override
	public Class<PersonContactDto> dtoClass() {
		return PersonContactDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.PERSON;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		Optional<PersonContact> perContact = perContactRepo.getByPId(query.getPersonId());
		if(perContact.isPresent())
			return PersonContactDto.createFromDomain(perContact.get());
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
		List<GridPeregDomainDto> result = new ArrayList<>();
		// key - pid , value - sid getPersonId getEmployeeId
		Map<String, String> mapSids = query.getEmpInfos().stream()
				.collect(Collectors.toMap(PeregEmpInfoQuery::getPersonId, PeregEmpInfoQuery::getEmployeeId));
		List<PersonContact> personContactLst = perContactRepo
				.getByPersonIdList(new ArrayList<String>(mapSids.keySet()));

		personContactLst.stream().forEach(c -> {
			result.add(new GridPeregDomainDto(mapSids.get(c.getPersonId()), c.getPersonId(),
					PersonContactDto.createFromDomain(c)));
		});

		if (query.getEmpInfos().size() > result.size()) {
			for (int i = result.size(); i < query.getEmpInfos().size(); i++) {
				PeregEmpInfoQuery emp = query.getEmpInfos().get(i);
				result.add(new GridPeregDomainDto(emp.getEmployeeId(), emp.getPersonId(), null));
			}
		}

		return result;
	}
}
