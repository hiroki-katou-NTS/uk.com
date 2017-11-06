package nts.uk.screen.at.ws.schedule.basicschedule;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenParams;
import nts.uk.screen.at.app.schedule.basicschedule.BasicScheduleScreenProcessor;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTimeScreenDto;
import nts.uk.screen.at.app.schedule.basicschedule.WorkTypeScreenDto;

/**
 * 
 * @author sonnh1
 *
 */
@Path("screen/at/schedule/basicschedule")
@Produces("application/json")
public class Ksu001Webservice extends WebService {

	@Inject
	private BasicScheduleScreenProcessor bScheduleScreenProces;

	@POST
	@Path("getData")
	public List<BasicScheduleScreenDto> getData(BasicScheduleScreenParams params) {
		return this.bScheduleScreenProces.getByListSidAndDate(params);
	}

	@POST
	@Path("getListWorkTime")
	public List<WorkTimeScreenDto> getWorkTime() {
		return this.bScheduleScreenProces.getListWorkTime();
	}

	/**
	 * Gets worktype base on Cid and deprecateCls
	 *
	 * @return the by Cid and deprecateCls
	 */
	@POST
	@Path("getListWorkType")
	public List<WorkTypeScreenDto> getByCIdAndDeprecateCls() {
		return this.bScheduleScreenProces.findByCIdAndDeprecateCls();
	}
}
