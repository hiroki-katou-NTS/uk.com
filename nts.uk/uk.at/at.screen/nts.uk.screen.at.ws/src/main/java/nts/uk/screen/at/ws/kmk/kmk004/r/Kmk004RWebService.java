package nts.uk.screen.at.ws.kmk.kmk004.r;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetemp.CopyMonthlyWorkTimeSetEmpCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetsha.CopyMonthlyWorkTimeSetShaCommandHandler;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.CopyMonthlyWorkTimeSetCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetwkp.CopyMonthlyWorkTimeSetWkpCommandHandler;

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
	public void copyWkp(CopyMonthlyWorkTimeSetCommand command) {
		this.copyWkpHandlder.handle(command);
	}

	@POST
	@Path("copy-emp")
	public void copyEmp(CopyMonthlyWorkTimeSetCommand command) {
		this.copyEmpHandlder.handle(command);
	}

	@POST
	@Path("copy-sha")
	public void copySha(CopyMonthlyWorkTimeSetCommand command) {
		this.copyShaHandlder.handle(command);
	}
}
