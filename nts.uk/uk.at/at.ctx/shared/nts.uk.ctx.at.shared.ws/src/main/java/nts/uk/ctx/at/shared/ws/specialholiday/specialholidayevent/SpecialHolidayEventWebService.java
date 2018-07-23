package nts.uk.ctx.at.shared.ws.specialholiday.specialholidayevent;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.DeleteSpecialHolidayEventCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.DeleteSpecialHolidayEventCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.SaveSpecialHolidayEventCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.SaveSpecialHolidayEventCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayEventFinder;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.SpecialHolidayFrameWithSettingDto;

@Path("shared/specialholiday/specialholidayevent")
@Produces("application/json")
public class SpecialHolidayEventWebService extends WebService {

	@Inject
	private SpecialHolidayEventFinder sHEventFinder;
	@Inject
	private SaveSpecialHolidayEventCommandHandler sHECmdHanler;
	@Inject
	private DeleteSpecialHolidayEventCommandHandler sHECmdDelHanler;

	@Path("getFrames")
	@POST
	public List<SpecialHolidayFrameWithSettingDto> getFrames() {
		return this.sHEventFinder.getFrames();
	}

	@Path("changeSpecialEvent/{noSelected}")
	@POST
	public SpecialHolidayEventDto changeSpecialEvent(@PathParam("noSelected") int noSelected) {
		return this.sHEventFinder.changeSpecialEvent(noSelected);
	}

	@Path("save")
	@POST
	public void saveSpecialHolidayEvent(SaveSpecialHolidayEventCommand command) {
		this.sHECmdHanler.handle(command);
	}

	@Path("delete/{noDelete}")
	@POST
	public void saveSpecialHolidayEvent(@PathParam("noDelete") int noDelete) {
		this.sHECmdDelHanler.handle(new DeleteSpecialHolidayEventCommand(noDelete));
	}

}
