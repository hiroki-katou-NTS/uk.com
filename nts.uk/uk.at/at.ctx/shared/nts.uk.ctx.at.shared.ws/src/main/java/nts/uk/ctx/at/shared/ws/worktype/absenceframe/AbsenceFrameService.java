package nts.uk.ctx.at.shared.ws.worktype.absenceframe;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.command.worktype.absenceframe.AbsenceFrameCommand;
import nts.uk.ctx.at.shared.app.command.worktype.absenceframe.AbsenceFrameUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.worktype.absenceframe.AbsenceFrameDto;
import nts.uk.ctx.at.shared.app.find.worktype.absenceframe.AbsenceFrameFinder;

/**
 * The Class AbsenceFrameService.
 */
@Path("at/share/worktype/absenceframe")
@Produces("application/json")
public class AbsenceFrameService {
	/** The find command handler. */
	@Inject
	private AbsenceFrameFinder find;
	
	/** The update command handler. */
	@Inject
	private AbsenceFrameUpdateCommandHandler update;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<AbsenceFrameDto> findAll() {
		return this.find.findAll();
	}
	
	/**
	 * Find by frame no.
	 *
	 * @return the data
	 */
	@POST
	@Path("findAbsenceFrameByCode/{frameNo}")
	public AbsenceFrameDto findAbsenceFrameByCode(@PathParam("frameNo") int frameNo) {
		return this.find.findAbsenceFrameByCode(frameNo);
	}
	
	/**
	 * Update Absence Frame.
	 *
	 * @param command the command
	 */
	@POST
	@Path("updateAbsenceFrame")
	public void updateAbsenceFrame(AbsenceFrameCommand command) {
		update.handle(command);
	}
}
