package nts.uk.ctx.pereg.app.find.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import nts.uk.ctx.bs.employee.app.find.person.PersonLayoutDto;
import nts.uk.shr.pereg.app.find.PeregCtgListFinder;
import nts.uk.shr.pereg.app.find.PeregCtgSingleFinder;
import nts.uk.shr.pereg.app.find.PeregFinderProcessorCollector;
import nts.uk.shr.pereg.app.find.PeregQuery;


@Stateless
@SuppressWarnings("serial")
public class PeregLayoutingProcessorCollectorImpl implements PeregFinderProcessorCollector{

	/** ctg single finder */
	private static final List<TypeLiteral<?>> FINDER_CTG_SINGLE_HANDLER_CLASSES = Arrays.asList(
			);
	
	/** ctg list finder */
	private static final List<TypeLiteral<?>> FINDER_CTG_LIST_HANDLER_CLASSES = Arrays.asList(
			);

	@Override
	public Set<PeregCtgSingleFinder<?, ?>> peregCtgSingleFinderCollect() {
		return FINDER_CTG_SINGLE_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregCtgSingleFinder<?, ?>)obj)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregCtgListFinder<?, ?>> peregCtgListFinderCollect() {
		return FINDER_CTG_LIST_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregCtgListFinder<?, ?>)obj)
				.collect(Collectors.toSet());
	}

}
