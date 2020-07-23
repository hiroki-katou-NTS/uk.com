package nts.uk.screen.at.ws.kdp.kdp005.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.kdp.kdp005.a.ICCardStampCommand;
import nts.uk.ctx.at.record.app.command.kdp.kdp005.a.ICCardStampCommandHandler;

/**
 * @author thanhpv
 *
 */
@Path("at/record/stamp/ICCardStamp")
@Produces("application/json")
public class ICCardStampWebService extends WebService {

	@Inject
	private ICCardStampCommandHandler commandHanler;

	@POST
	@Path("checks")
	public GeneralDate registerFingerStamp(ICCardStampCommand command) {
		return this.commandHanler.handle(command);
	}

}
