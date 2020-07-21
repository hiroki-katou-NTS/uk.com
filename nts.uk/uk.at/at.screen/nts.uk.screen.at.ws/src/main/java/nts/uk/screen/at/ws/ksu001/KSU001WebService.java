package nts.uk.screen.at.ws.ksu001;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksu001.SendingPeriodParams;
import nts.uk.screen.at.app.ksu001.SendingPeriodResult;
import nts.uk.screen.at.app.schedule.basicschedule.DataInitScreenDto;

/**
 * 
 * @author laitv
 *
 */
@Path("screen/at/schedule/schedule/start")
@Produces("application/json")
public class KSU001WebService extends WebService{

	@POST
	public DataInitScreenDto init() {
		return null;
	}
	
	@POST
	@Path("getSendingPeriod")
	public SendingPeriodResult getSendingPeriod(SendingPeriodParams param) {
		
		return null;
	}
	
}
