package nts.uk.screen.at.ws.kmk.kmk004.q;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.command.kmk.kmk004.a.RegisterYearCommand;
import nts.uk.screen.at.app.command.kmk.kmk004.a.RegisterYearCommandHandler;

@Path("screen/at/kmk004/q")
@Produces("application/json")
public class Kmk004QWebService {

	private RegisterYearCommandHandler cmdHanlder;

	@POST
	@Path("register-year")
	public void registerYear(RegisterYearCommand command) {
		this.cmdHanlder.handle(command);
	}
}
