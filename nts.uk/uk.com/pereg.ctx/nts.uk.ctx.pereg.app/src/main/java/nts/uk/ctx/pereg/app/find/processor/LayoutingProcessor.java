package nts.uk.ctx.pereg.app.find.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpOptRepository;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregPerOptRepository;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;
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
		if(finderClass == null) return null;
		PeregDomainDto domainDto = finderClass.findSingle(query);
		
		if ( domainDto == null ) {
			return null;
		}
		
		PeregDto peregDto = new PeregDto(domainDto, finderClass.dtoClass(), finderClass.dataType());
		// get optional data
		setUserDefData(peregDto);
		
		return peregDto;
		
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
		List<PeregDomainDto> lstDtos = finderClass.findList(query);
		
		List<PeregDto> peregDtos = new ArrayList<>();
		// get optional data
		lstDtos.stream().forEach(domainDto -> {
			PeregDto peregDto = new PeregDto(domainDto, finderClass.dtoClass(), finderClass.dataType());
			setUserDefData(peregDto);
			peregDtos.add(peregDto);
		});
		
		return peregDtos;
	}
	
	public List<ComboBoxObject> getListFirstItems(PeregQuery query){
		val finderClass = this.finders.get(query.getCategoryCode());
		return finderClass.getListFirstItems(query);
	}
	
	private void setUserDefData(PeregDto peregDto){		
		if( peregDto.getDataType() == DataClassification.PERSON)			
			peregDto.setPerOptionalData(perOptRepo.getData(peregDto.getDomainDto().getRecordId()));
		else 
			peregDto.setEmpOptionalData(empOptRepo.getData(peregDto.getDomainDto().getRecordId()));
		
	}

}
