package find.person.contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
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

		List<String> pids = query.getEmpInfos().stream().map(c -> c.getPersonId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});
		
		List<PersonContact> personContactLst = perContactRepo.getByPersonIdList(pids);

		result.stream().forEach(c -> {
			Optional<PersonContact> perOpt = personContactLst.stream()
					.filter(emp -> emp.getPersonId().equals(c.getPersonId())).findFirst();
			c.setPeregDomainDto(perOpt.isPresent() == true ? PersonContactDto.createFromDomain(perOpt.get()) : null);
		});

		return result;
	}

	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		List<GridPeregDomainBySidDto> result = new ArrayList<>();

		List<String> pids = query.getEmpInfos().stream().map(c -> c.getPersonId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainBySidDto(c.getEmployeeId(), c.getPersonId(), new ArrayList<>()));
		});
		
		List<PersonContact> personContactLst = perContactRepo.getByPersonIdList(pids);

		result.stream().forEach(c -> {
			Optional<PersonContact> perOpt = personContactLst.stream()
					.filter(emp -> emp.getPersonId().equals(c.getPersonId())).findFirst();
			c.setPeregDomainDto(perOpt.isPresent() == true ? Arrays.asList(PersonContactDto.createFromDomain(perOpt.get())) : new ArrayList<>());
		});

		return result;
	}
}
