package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenFinder;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenParams;

/**
 * 
 * @author sonnh1
 *
 */
@Path("screen/at/schedule/basicschedule")
@Produces("application/json")
public class Ksu001Webservice {
	
	@Inject
	private BasicScheduleScreenFinder bScheduleScreenFinder;
	
	@POST
	@Path("getData")
	public List<BasicScheduleScreenDto> getData(BasicScheduleScreenParams params) {
		return this.bScheduleScreenFinder.getByListSidAndDate(params);
	}
}
