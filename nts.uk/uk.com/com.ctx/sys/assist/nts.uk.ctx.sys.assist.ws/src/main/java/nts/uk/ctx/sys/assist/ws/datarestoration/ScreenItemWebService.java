package nts.uk.ctx.sys.assist.ws.datarestoration;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.datarestoration.EmployeeSelection;
import nts.uk.ctx.sys.assist.app.command.datarestoration.FindScreenItemCommand;
import nts.uk.ctx.sys.assist.app.find.datarestoration.ScreenItemFinder;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class ScreenItemWebService {
	@Inject
	private ScreenItemFinder screenItemFinder;

	@POST
	@Path("findPerformDataRecover")
	public EmployeeSelection findPerformDataRecover(FindScreenItemCommand command) {
		return screenItemFinder.getTargetById(command);
	}
}
