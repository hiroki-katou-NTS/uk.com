package nts.uk.ctx.at.shared.app.find.era.name;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.era.name.EraNameDom;
import nts.uk.ctx.at.shared.dom.era.name.EraNameDomRepository;

@Stateless
public class EraNameFinder {
	
	@Inject
	private EraNameDomRepository repository;
	
	public List<EraNameFindDto> getAllEraNameItem() {
		// get list era name domain
		List<EraNameDom> eraNameItems = this.repository.getAllEraName();
		
		// check empty
		if (eraNameItems.isEmpty()) {
			return null;
		}

		return eraNameItems.stream().map(e -> new EraNameFindDto(e.getEraNameId(), e.getEraName().toString(), e.getStartDate().toString(), e.getEndDate().toString(), e.getSymbol().toString(), e.getSystemType().value)).collect(Collectors.toList());
	}
	
	public EraNameFindDto getEraNameItem(String eraNameId) {
		// get era name domain
		EraNameDom eraNameDom = this.repository.getEraNameById(eraNameId);
		
		return new EraNameFindDto(eraNameDom.getEraNameId(), eraNameDom.getEraName().toString(), eraNameDom.getStartDate().toString(), eraNameDom.getEndDate().toString(), eraNameDom.getSymbol().toString(), eraNameDom.getSystemType().value);
	}
}