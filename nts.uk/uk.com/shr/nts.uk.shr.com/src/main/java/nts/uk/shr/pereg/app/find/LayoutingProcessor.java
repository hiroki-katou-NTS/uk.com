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

	private Map<String, PeregFinder<?>> finders;

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
	public PeregResult findSingle(PeregQuery query) {
		val finderClass = this.finders.get(query.getCategoryCode());
		val dto = finderClass.findSingle(query);
		return new PeregResult(finderClass.dtoClass(), dto);
	}

	/**
	 * Ctg list handler
	 * 
	 * @param query
	 * @return
	 */
	public PeregResult findList(PeregQuery query) {
		val finderClass = this.finders.get(query.getCategoryCode());
		val dto = finderClass.findList(query);
		return new PeregResult(finderClass.dtoClass(), dto);
	}

}
