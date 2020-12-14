package nts.uk.screen.at.ws.kmk.kmk004.r;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetemp.CopyMonthlyWorkTimeSetEmpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetemp.CopyMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.CopyMonthlyWorkTimeSetShaCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetsha.CopyMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp.CopyMonthlyWorkTimeSetWkpCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.monthlyworktimesetwkp.CopyMonthlyWorkTimeSetWkpCommandHandler;

@Path("screen/at/kmk004/r")
@Produces("application/json")
public class Kmk004RWebService {

	@Inject
	private CopyMonthlyWorkTimeSetWkpCommandHandler copyWkpHandlder;
	@Inject
	private CopyMonthlyWorkTimeSetEmpCommandHandler copyEmpHandlder;
	@Inject
	private CopyMonthlyWorkTimeSetShaCommandHandler copyShaHandlder;

	@POST
	@Path("copy-wkp")
	public void copyWkp(CopyMonthlyWorkTimeSetWkpCommand command) {
		this.copyWkpHandlder.handle(command);
	}

	@POST
	@Path("copy-emp")
	public void copyEmp(CopyMonthlyWorkTimeSetEmpCommand command) {
		this.copyEmpHandlder.handle(command);
	}

	@POST
	@Path("copy-sha")
	public void copySha(CopyMonthlyWorkTimeSetShaCommand command) {
		this.copyShaHandlder.handle(command);
	}
}
