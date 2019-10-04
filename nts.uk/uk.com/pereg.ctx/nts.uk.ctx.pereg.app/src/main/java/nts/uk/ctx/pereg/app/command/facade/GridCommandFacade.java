package nts.uk.ctx.pereg.app.command.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.error.ErrorWarningEmployeeInfo;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.pereg.app.command.EmployeeInputContainer;
import nts.uk.shr.pereg.app.command.GridInputContainer;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregInputContainerCps003;

@ApplicationScoped
public class GridCommandFacade {
	@Inject
	private PeregCommonCommandFacade commandFacade;
	public Collection<?> registerHandler(GridInputContainer gridInputContainer) {
		List<PeregInputContainerCps003> containerLst = new ArrayList<>();
		gridInputContainer.getEmployees().stream().forEach(c -> {
			containerLst.add(new PeregInputContainerCps003(c.getPersonId(), c.getEmployeeId(), c.getInput()));
		});
		List<MyCustomizeException> errorLst = this.commandFacade.registerHandler(containerLst,
				gridInputContainer.getEditMode(), gridInputContainer.getBaseDate());
		List<Object> result = new ArrayList<>();

		errorLst.stream().forEach(c ->{
			c.getErrorLst().stream().forEach(e ->{
				Optional<EmployeeInputContainer> emp = gridInputContainer.getEmployees().stream().filter(i -> i.getEmployeeId().equals(e)).findFirst();
				if(emp.isPresent()) {
					//0 - error
					//1 - warning
					ErrorWarningEmployeeInfo errorWarning = new ErrorWarningEmployeeInfo(emp.get().getEmployeeId(),
							emp.get().getEmployeeCd(), emp.get().getEmployeeName(), emp.get().getOrder(), true, 0,  c.getItemName(), c.getMessageId().equals("Msg_1577") == true? TextResource.localize("Msg_1577", Arrays.asList(emp.get().getInput().getCategoryName())):c.getMessage());	
					result.add(errorWarning);
				}
						
			});
			
		});
		return result;

	}
}
