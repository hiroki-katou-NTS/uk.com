package nts.uk.shr.sample.pereg.command.collect;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import nts.gul.reflection.ReflectionUtil;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregCommandHandlerCollector;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;
import nts.uk.shr.sample.pereg.command.SampleUpdatePersonBaseCommandHandler;

//@Stateless
public class SamplePeregCommandHandlerCollector implements PeregCommandHandlerCollector {

	/* BEGIN add handlers */
	
	/* END add handlers */
	
	
	/* BEGIN update handlers */
	
	//@Inject
	private SampleUpdatePersonBaseCommandHandler updatePersonBase; // this is sample, plz remove later
	
	/* END update handlers */
	
	
	/* BEGIN delete handlers */
	
	/* END delete handlers */
	
	
	@Override
	public Set<PeregAddCommandHandler<?>> collectAddHandlers() {
		return Arrays.asList(SamplePeregCommandHandlerCollector.class.getFields()).stream()
				.map(f -> ReflectionUtil.getFieldValue(f, this))
				.filter(fv -> fv instanceof PeregAddCommandHandler)
				.map(fv -> (PeregAddCommandHandler<?>)fv)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<PeregUpdateCommandHandler<?>> collectUpdateHandlers() {
		return Arrays.asList(SamplePeregCommandHandlerCollector.class.getFields()).stream()
				.map(f -> ReflectionUtil.getFieldValue(f, this))
				.filter(fv -> fv instanceof PeregUpdateCommandHandler)
				.map(fv -> (PeregUpdateCommandHandler<?>)fv)
				.collect(Collectors.toSet());
	}

}
