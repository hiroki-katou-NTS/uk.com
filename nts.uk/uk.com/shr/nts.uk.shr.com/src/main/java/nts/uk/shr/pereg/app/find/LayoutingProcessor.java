package nts.uk.shr.pereg.app.find;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.pereg.app.find.dto.PeregDto;
import nts.uk.shr.pereg.app.find.dto.PersonOptionalDto;

@ApplicationScoped
public class LayoutingProcessor {

	@Inject
	private PeregFinderProcessorCollector peregFinderCollector;

	private Map<String, PeregFinder<?>> finders;
	
	@Inject
	private PeregEmpUserDefFinderRepository peregEmpUserDefFinderRepository;
	
	@Inject 
	private PeregPerUserDefFinderRepository peregPerUserDefFinderRepository;

	/**
	 * Initializes.
	 */
	public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
		this.finders = this.peregFinderCollector.peregFinderCollect().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCode(), h -> h));
	}

	/**
	 * Ctg single handler
	 * 
	 * @param query
	 * @return
	 */
	public PeregDto findSingle(PeregQuery query) {
		val finderClass = this.finders.get(query.getCategoryCode());
		PeregDto dto = finderClass.findSingle(query);
		setUserDefData(dto);
		return dto;
	}

	/**
	 * Ctg list handler
	 * 
	 * @param query
	 * @return
	 */
	public List<PeregDto> findList(PeregQuery query) {
		val finderClass = this.finders.get(query.getCategoryCode());
		List<PeregDto> lstDtos = finderClass.findList(query);
		lstDtos.stream().forEach(dto -> {
			setUserDefData(dto);
		});
		return lstDtos;
	}
	
	private void setUserDefData(PeregDto dto){
		if(dto.getEmpPerCtgType() == 1)			
			dto.setPerOptionalData(peregPerUserDefFinderRepository.getPersonOptionalData(dto.getDomainDto().getRecordId()));
		else 
			dto.setEmpOptionalData(peregEmpUserDefFinderRepository.getEmpOptionalDto(dto.getDomainDto().getRecordId()));
	}

}
