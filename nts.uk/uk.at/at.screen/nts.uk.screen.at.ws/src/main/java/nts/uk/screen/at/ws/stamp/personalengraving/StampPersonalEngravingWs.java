package nts.uk.screen.at.ws.stamp.personalengraving;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.stamp.personalengraving.query.StampResultConfirmDto;
import nts.uk.screen.at.app.stamp.personalengraving.query.StampResultConfirmRequest;
import nts.uk.screen.at.app.stamp.personalengraving.query.StampResultConfirmationQuery;

/**
 * TODO
 */
@Path("screen/at/personalengraving")
@Produces("application/json")
public class StampPersonalEngravingWs {
	
	@Inject 
	private StampResultConfirmationQuery stampResultConfirmationQuery;
	
	@POST
	@Path("startCScreen")
	public StampResultConfirmDto startScreen(StampResultConfirmRequest param) throws InterruptedException {
		return stampResultConfirmationQuery.getStampResultConfirm(param);
	}
}
