package nts.uk.ctx.at.shared.ws.specialholiday.specialholidayevent.grantdayperrelationship;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship.DeleteGrantDayRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship.DeleteGrantDayRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship.SaveGrantDayRelationshipCommand;
import nts.uk.ctx.at.shared.app.command.specialholiday.specialholidayevent.grantdayperrelationship.SaveGrantDayRelationshipCommandHandler;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationshipDto;
import nts.uk.ctx.at.shared.app.find.specialholiday.specialholidayevent.grantdayperrelationship.GrantDayRelationshipFinder;

@Path("shared/specialholiday/specialholidayevent/grant-day-per-relationship")
@Produces("application/json")
public class GrantDayPerRelationshipWebservice extends WebService {

	@Inject
	private GrantDayRelationshipFinder finder;
	@Inject
	private SaveGrantDayRelationshipCommandHandler saveHandler;
	@Inject
	private DeleteGrantDayRelationshipCommandHandler deleteHandler;

	@Path("change-special-event/{sHENo}/{relpCd}")
	@POST
	public GrantDayRelationshipDto findByKey(@PathParam("sHENo") int sHENo, @PathParam("relpCd") String relpCd) {
		return this.finder.findByKey(sHENo, relpCd);
	}

	@Path("save")
	@POST
	public void save(SaveGrantDayRelationshipCommand command) {
		this.saveHandler.handle(command);
	}

	@Path("delete/{sHENo}/{relpCd}")
	@POST
	public void delete(@PathParam("sHENo") int sHENo, @PathParam("relpCd") String relpCd) {
		this.deleteHandler.handle(new DeleteGrantDayRelationshipCommand(relpCd, sHENo));
	}

}
