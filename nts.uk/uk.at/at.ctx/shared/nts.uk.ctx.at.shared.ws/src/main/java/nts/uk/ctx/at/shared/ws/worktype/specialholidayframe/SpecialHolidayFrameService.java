package nts.uk.ctx.at.shared.ws.worktype.specialholidayframe;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.worktype.specialholidayframe.SpecialHolidayFrameCommand;
import nts.uk.ctx.at.shared.app.command.worktype.specialholidayframe.SpecialHolidayFrameUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameDto;
import nts.uk.ctx.at.shared.app.find.worktype.specialholidayframe.SpecialHolidayFrameFinder;

/**
 * The Class SpecialHolidayService.
 */
@Path("at/share/worktype/specialholidayframe")
@Produces("application/json")
public class SpecialHolidayFrameService {
	/** The find command handler. */
	@Inject
	private SpecialHolidayFrameFinder find;
	
	/** The update command handler. */
	@Inject
	private SpecialHolidayFrameUpdateCommandHandler update;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<SpecialHolidayFrameDto> findAll() {
		return this.find.findAll();
	}
	
	/**
	 * Find by frame no.
	 *
	 * @return the data
	 */
	@POST
	@Path("findHolidayFrameByCode/{frameNo}")
	public SpecialHolidayFrameDto findHolidayFrameByCode(@PathParam("frameNo") int frameNo) {
		return this.find.findHolidayFrameByCode(frameNo);
	}
	
	/**
	 * Update Special Holiday Frame.
	 *
	 * @param command the command
	 */
	@POST
	@Path("updateSpecialHolidayFrame")
	public void updateSpecialHolidayFrame(SpecialHolidayFrameCommand command) {
		update.handle(command);
	}
}
