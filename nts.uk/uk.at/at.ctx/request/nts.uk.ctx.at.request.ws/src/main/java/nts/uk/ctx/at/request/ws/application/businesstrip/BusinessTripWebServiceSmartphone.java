package nts.uk.ctx.at.request.ws.application.businesstrip;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.application.businesstrip.AddBusinessTripCommandHandler;
import nts.uk.ctx.at.request.app.command.application.workchange.AddAppWorkChangeCommandHandler;
import nts.uk.ctx.at.request.app.find.application.businesstrip.AppBusinessParam;
import nts.uk.ctx.at.request.app.find.application.businesstrip.BusinessTripFinder;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripInfoOutputDto;
import nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto.BusinessTripOutputDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeFinder;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeOutputDto;
import nts.uk.ctx.at.request.app.find.application.workchange.AppWorkChangeParam;


@Path("at/request/application/businesstrip/mobile")
@Produces("application/json")
public class BusinessTripWebServiceSmartphone extends WebService {
	@Inject
	private AddBusinessTripCommandHandler addHandler;

	@Inject
	private BusinessTripFinder businessTripFinder;
	
	@Path("startMobile")
	@POST
	public BusinessTripOutputDto startMobile(AppBusinessParam appWorkChangeParam) {
		return businessTripFinder.startKAFS08(appWorkChangeParam);
	}
	
}
