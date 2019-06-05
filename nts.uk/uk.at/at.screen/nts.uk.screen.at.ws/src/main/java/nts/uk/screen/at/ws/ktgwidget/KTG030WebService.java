package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ktgwidget.KTG030QueryProcessor;

@Path("screen/at/ktg030")
@Produces("application/json")
public class KTG030WebService extends WebService {

	@Inject
	private KTG030QueryProcessor queryProcessor;

	@POST
	@Path("checkDisplay/{ym}")
	public boolean checkDisplay(@PathParam("ym")int ym) {
		return queryProcessor.checkDataMonPerConfirm(ym);
	}
}