package nts.uk.screen.at.ws.stamp.personalengraving;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterDailyIDentifyCommand;
import nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving.RegisterDailyIDentifyCommandHandler;
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
	
	@Inject
	private RegisterDailyIDentifyCommandHandler registerDailyIDentifyCommandHandler;
	
	@POST
	@Path("startCScreen")
	public StampResultConfirmDto startScreen(StampResultConfirmRequest param) throws InterruptedException {
		return stampResultConfirmationQuery.getStampResultConfirm(param);
	}
//	@POST
//	@Path("startCScreen")
//	public StampResultConfirmDto startScreen(List<Integer> attendanceItems) throws InterruptedException {
//		StampResultConfirmRequest param = new StampResultConfirmRequest(GeneralDate.today().toString(), attendanceItems);
//		return stampResultConfirmationQuery.getStampResultConfirm(param);
//		
//	}
	
	@POST
	@Path("registerDailyIdentify")
	public void registerDailyIdentify(RegisterDailyIDentifyCommand param) throws InterruptedException {
		registerDailyIDentifyCommandHandler.handle(param);
	}
	
	
}
