package nts.uk.shr.pereg.app.find;

import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import lombok.val;

@ApplicationScoped
public class LayoutingProcessor {
	
	@Inject
	private PeregFinderProcessorCollector peregFinderCollector;
	
	private Map<String, PeregFinder<?, ?>> peregProcess;
	
	/**
	 * Initializes.
	 */
	public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
		this.peregProcess = this.peregFinderCollector.peregFinderCollect().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCode(), h -> h));
	}
	
	/**
	 * 
	 */
	public LayoutingResult handler(PeregQuery query){
		val finderClass = this.peregProcess.get(query.getCtgCd());
 		val domainData = finderClass.handleProcessor(query);
		return new LayoutingResult(finderClass.finderClass(), domainData);
	}
	
}
