package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.uk.shr.pereg.app.command.GridInputContainer;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@ApplicationScoped
public class GridCommandFacade  {
	@Inject
	private PeregCommandFacade commandFacade;
	
	@Inject
	private ManagedParallelWithContext parallel;
	
	@SuppressWarnings("finally")
	public Collection<?> registerHandler(GridInputContainer gridInputContainer) {
		List<Object> resultsSync = Collections.synchronizedList(new ArrayList<>());
		int size = gridInputContainer.getEmployees().size();
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		try {
			this.parallel.forEach(gridInputContainer.getEmployees(), input -> {
				PeregInputContainer container = new PeregInputContainer(input.getPersonId(), input.getEmployeeId(),
						Arrays.asList(input.getInput()));
				try {
					Object obj = commandFacade.registerHandler(container);
					resultsSync.add(obj);
					
				}catch (Throwable t) {
					BusinessException message = new BusinessException("Msg_824", input.getEmployeeId());
					message.setSuppliment("NameID", input.getEmployeeId());
					exceptions.addMessage(message);
					if(gridInputContainer.getEmployees().get(size - 1).getEmployeeId().equals(input.getEmployeeId())) {
//						return gridInputContainer.getEmployees();
						exceptions.throwExceptions(); 
					} 
		        }
				
			});
			
		}catch (Throwable t) {
			throw t;
		}
		
		finally {
			if (exceptions.cloneExceptions().size() == 0) {
				return resultsSync;
			} else {
				return gridInputContainer.getEmployees();
			}
		}
		


	}
}
