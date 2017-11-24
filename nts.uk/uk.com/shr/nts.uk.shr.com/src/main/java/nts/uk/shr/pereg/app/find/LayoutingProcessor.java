package nts.uk.shr.pereg.app.find;

import java.util.Map;
import java.util.stream.Collectors;


import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.val;

@ApplicationScoped
public class LayoutingProcessor {
	
	@Inject
	private PeregFinderProcessorCollector peregFinderCollector;
	
	private Map<String, PeregCtgSingleFinder<?, ?>> peregCtgSingleProcess;
	
	private Map<String, PeregCtgListFinder<?, ?>> peregCtgListProcess;
	
	/**
	 * Initializes.
	 */
	public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
		this.peregCtgSingleProcess = this.peregFinderCollector.peregCtgSingleFinderCollect().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCode(), h -> h));
		
		this.peregCtgListProcess = this.peregFinderCollector.peregCtgListFinderCollect().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCode(), h -> h));
	}
	
	/**
	 * Ctg single handler
	 * 
	 * @param query
	 * @return
	 */
	public LayoutingResult ctgSingleHandler(PeregQuery query){
		val finderClass = this.peregCtgSingleProcess.get(query.getCtgCd());
 		val queryResult = finderClass.handleCtgSingProcessor(query);
		return new LayoutingResult(finderClass.finderClass(), queryResult);
	}
	
	/**
	 * Ctg list handler
	 * 
	 * @param query
	 * @return
	 */
	public LayoutingResult ctgListHandler(PeregQuery query){
		val finderClass = this.peregCtgListProcess.get(query.getCtgCd());
 		val queryResult = finderClass.handleCtgListProcessor(query);
		return new LayoutingResult(finderClass.finderClass(), queryResult);
	}
	
}
