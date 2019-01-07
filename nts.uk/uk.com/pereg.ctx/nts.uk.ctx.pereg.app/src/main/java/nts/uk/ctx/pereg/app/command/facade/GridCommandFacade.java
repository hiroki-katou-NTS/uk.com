package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.uk.shr.pereg.app.command.GridInputContainer;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@ApplicationScoped
public class GridCommandFacade {
	@Inject
	private PeregCommandFacade commandFacade;
	
	@Inject
	private ManagedParallelWithContext parallel;
	
	public List<Object> registerHandler(GridInputContainer gridInputContainer) {
		List<Object> resultsSync = Collections.synchronizedList(new ArrayList<>());
		this.parallel.forEach(gridInputContainer.getEmployees(), input -> {
			PeregInputContainer container = new PeregInputContainer(input.getPersonId(), input.getEmployeeId(),
					Arrays.asList(input.getInput()));
			commandFacade.registerHandler(container);
			resultsSync.add(commandFacade.registerHandler(container));
		});
		return resultsSync;
	}
}
