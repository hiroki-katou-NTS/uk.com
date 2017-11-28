package nts.uk.shr.pereg.app.find;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDto;

@ApplicationScoped
public class LayoutingProcessor {

	@Inject
	private PeregFinderProcessorCollector peregFinderCollector;

	private Map<String, PeregFinder<?>> finders;
	
	@Inject
	private PeregEmpOptRepository empOptRepo;
	
	@Inject 
	private PeregPerOptRepository perOptRepo;

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
		
		// get domain data
		val finderClass = this.finders.get(query.getCategoryCode());
		PeregDto dto = finderClass.findSingle(query);
		
		// get optional data
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
		
		// get domain data
		val finderClass = this.finders.get(query.getCategoryCode());
		List<PeregDto> lstDtos = finderClass.findList(query);
		
		// get optional data
		lstDtos.stream().forEach(dto -> {
			setUserDefData(dto);
		});
		
		return lstDtos;
	}
	
	private void setUserDefData(PeregDto dto){
		if(dto.getDataType() == DataClassification.PERSON)			
			dto.setPerOptionalData(perOptRepo.getData(dto.getDomainDto().getRecordId()));
		else 
			dto.setEmpOptionalData(empOptRepo.getData(dto.getDomainDto().getRecordId()));
	}

}
