package nts.uk.ctx.pereg.app.find.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import nts.uk.shr.pereg.app.find.PeregListFinder;
import nts.uk.shr.pereg.app.find.PeregSingleFinder;
import nts.uk.shr.pereg.app.find.PeregFinderProcessorCollector;


@Stateless
public class PeregLayoutingProcessorCollectorImpl implements PeregFinderProcessorCollector{

	/** ctg single finder */
	private static final List<TypeLiteral<?>> FINDER_CTG_SINGLE_HANDLER_CLASSES = Arrays.asList(
			);
	
	/** ctg list finder */
	private static final List<TypeLiteral<?>> FINDER_CTG_LIST_HANDLER_CLASSES = Arrays.asList(
			);

	@Override
	public Set<PeregSingleFinder> peregCtgSingleFinderCollect() {
		return FINDER_CTG_SINGLE_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregSingleFinder)obj)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregListFinder> peregCtgListFinderCollect() {
		return FINDER_CTG_LIST_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregListFinder)obj)
				.collect(Collectors.toSet());
	}

}
