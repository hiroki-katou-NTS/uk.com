package nts.uk.ctx.at.shared.ws.specialholiday.specialholidayevent;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventFinder;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayFrameWithSettingDto;

@Path("shared/specialholidayevent")
@Produces("application/json")
public class SpecialHolidayEventWebService extends WebService {

	@Inject
	private SpecialHolidayEventFinder sHEventFinder;

	@Path("startPage")
	@POST
	public List<SpecialHolidayFrameWithSettingDto> startPage() {
		return this.sHEventFinder.startPage();
	}

	@Path("changeSpecialEvent/{noSelected}")
	@POST
	public SpecialHolidayEventDto changeSpecialEvent(@PathParam("noSelected") int noSelected) {
		return this.sHEventFinder.changeSpecialEvent(noSelected);
	}

}
