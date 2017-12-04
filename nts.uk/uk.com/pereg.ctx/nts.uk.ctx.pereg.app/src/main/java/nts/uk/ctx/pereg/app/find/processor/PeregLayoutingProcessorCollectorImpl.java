package nts.uk.ctx.pereg.app.find.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import nts.uk.ctx.bs.employee.app.find.person.PersonLayoutDto;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;


@Stateless
@SuppressWarnings("serial")
public class PeregLayoutingProcessorCollectorImpl implements PeregFinderProcessorCollector{

	/** finder */
	private static final List<TypeLiteral<?>> FINDER_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregFinder<PersonLayoutDto, PeregQuery>>(){}
			);
	
	@Override
	public Set<PeregFinder<?, ?>> peregFinderCollect() {
		return FINDER_HANDLER_CLASSES.stream()
				.map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregFinder<?, ?>)obj)
				.collect(Collectors.toSet());
	}

}
