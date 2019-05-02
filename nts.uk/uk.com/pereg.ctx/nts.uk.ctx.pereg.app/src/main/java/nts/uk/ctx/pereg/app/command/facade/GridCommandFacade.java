package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import nts.uk.shr.pereg.app.command.GridInputContainer;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregInputContainerCps003;

@ApplicationScoped
public class GridCommandFacade {
	@Inject
	private PeregCommonCommandFacade commandFacade;

	@SuppressWarnings("finally")
	public Collection<?> registerHandler(GridInputContainer gridInputContainer) {
		
		List<Object> resultsSync = Collections.synchronizedList(new ArrayList<>());
		
		List<MyCustomizeException> errorLstSync = Collections.synchronizedList(new ArrayList<>());
		List<PeregInputContainerCps003>  containerLst = new ArrayList<>();
		
		try { 
			
			gridInputContainer.getEmployees().parallelStream().forEach(c ->{
				containerLst.add(new PeregInputContainerCps003(c.getPersonId(), c.getEmployeeId(), c.getInput()));
			});
			this.commandFacade.registerHandler(containerLst, gridInputContainer.getEditMode(), gridInputContainer.getBaseDate());
		} catch (Throwable t) {
			
			throw t;
			
		} finally {
			
			if (errorLstSync.size() > 0) {
				
				return errorLstSync;
				
			}
			
			return resultsSync;
		}

	}
}
