package nts.uk.ctx.pereg.app.find.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import nts.uk.shr.pereg.app.find.PeregFinderProcessorCollector;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;


@Stateless
@SuppressWarnings("serial")
public class PeregLayoutingProcessorCollectorImpl implements PeregFinderProcessorCollector{

	/** finder */
	private static final List<TypeLiteral<?>> FINDER_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregFinder<Object, PeregQuery>>(){}
			);
	
	@Override
	public Set<PeregFinder<?, ?>> peregFinderCollect() {
		return FINDER_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregFinder<?, ?>)obj)
				.collect(Collectors.toSet());
	}

}
