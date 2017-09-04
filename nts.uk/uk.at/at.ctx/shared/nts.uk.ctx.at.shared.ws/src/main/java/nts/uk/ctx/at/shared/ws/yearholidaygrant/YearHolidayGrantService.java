package nts.uk.ctx.at.shared.ws.yearholidaygrant;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.yearholidaygrant.YearHolidayGrantAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.yearholidaygrant.YearHolidayGrantCommand;
import nts.uk.ctx.at.shared.app.command.yearholidaygrant.YearHolidayGrantDeleteCommand;
import nts.uk.ctx.at.shared.app.command.yearholidaygrant.YearHolidayGrantDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.yearholidaygrant.YearHolidayGrantUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.find.yearholidaygrant.YearHolidayGrantDto;
import nts.uk.ctx.at.shared.app.find.yearholidaygrant.YearHolidayGrantFinder;

/**
 * The Class YearHolidayGrantService.
 */
@Path("at/share/yearholidaygrant")
@Produces("application/json")
public class YearHolidayGrantService extends WebService {
	/** The find command handler. */
	@Inject
	private YearHolidayGrantFinder find;
	
	/** The add command handler. */
	@Inject
	private YearHolidayGrantAddCommandHandler add;
	
	/** The update command handler. */
	@Inject
	private YearHolidayGrantUpdateCommandHandler update;
	
	/** The delete command handler. */
	@Inject
	private YearHolidayGrantDeleteCommandHandler delete;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<YearHolidayGrantDto> findAll() {
		return this.find.findAll();
	}
	
	/**
	 * Find by code.
	 *
	 * @param yearHolidayCode the year holiday code
	 * @return the Year Holiday Grant Dto
	 */
	@POST
	@Path("findByCode/{yearHolidayCode}")
	public YearHolidayGrantDto findByCode(@PathParam("yearHolidayCode") String yearHolidayCode){
		return this.find.findByCode(yearHolidayCode);
	}
	
	/**
	 * Add new year holiday grant.
	 *
	 * @param command the command
	 */
	@POST
	@Path("add")
	public void addYearHolidayGrant(YearHolidayGrantCommand command) {
		add.handle(command);
	}
	
	/**
	 * Update year holiday grant.
	 *
	 * @param command the command
	 */
	@POST
	@Path("update")
	public void updateYearHolidayGrant(YearHolidayGrantCommand command) {
		update.handle(command);
	}
	
	/**
	 * Delete year holiday grant.
	 *
	 * @param command the command
	 */
	@POST
	@Path("delete")
	public void deleteYearHolidayGrant(YearHolidayGrantDeleteCommand command) {
		delete.handle(command);
	}
}
