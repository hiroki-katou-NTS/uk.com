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

	private Map<String, PeregCtgSingleFinder> singleFinders;

	private Map<String, PeregCtgListFinder> listFinders;

	/**
	 * Initializes.
	 */
	public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
		this.singleFinders = this.peregFinderCollector.peregCtgSingleFinderCollect().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCode(), h -> h));

		this.listFinders = this.peregFinderCollector.peregCtgListFinderCollect().stream()
				.collect(Collectors.toMap(h -> h.targetCategoryCode(), h -> h));
	}

	/**
	 * Ctg single handler
	 * 
	 * @param query
	 * @return
	 */
	public PeregResult findSingle(PeregQuery query) {
		val finderClass = this.singleFinders.get(query.getCategoryCode());
		val dto = finderClass.find(query);
		return new PeregResult(finderClass.dtoClass(), dto);
	}

	/**
	 * Ctg list handler
	 * 
	 * @param query
	 * @return
	 */
	public PeregResult findList(PeregQuery query) {
		val finderClass = this.listFinders.get(query.getCategoryCode());
		val dto = finderClass.find(query);
		return new PeregResult(finderClass.dtoClass(), dto);
	}

}
