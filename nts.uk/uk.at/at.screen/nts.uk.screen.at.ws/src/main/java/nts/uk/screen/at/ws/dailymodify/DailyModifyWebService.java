package nts.uk.screen.at.ws.dailymodify;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.dailymodify.command.DailyModifyCommandFacade;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;


@Path("dailymodify")
@Produces("application/json") 
public class DailyModifyWebService {

	@Inject
	private DailyModifyCommandFacade facade;
	
	@POST
	@Path("register")
	public void register(DailyModifyQuery query) {
		facade.handleAdd(query);
	}
}
