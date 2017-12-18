package nts.uk.ctx.at.schedule.ws.shift.team;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.team.TeamAddCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.team.TeamCommand;
import nts.uk.ctx.at.schedule.app.command.shift.team.TeamDeleteCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.team.TeamUpdateCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.team.TeamDto;
import nts.uk.ctx.at.schedule.app.find.shift.team.TeamFinder;

@Path("at/schedule/shift/team")
@Produces(MediaType.APPLICATION_JSON)
public class TeamWebService extends WebService {
	@Inject
	private TeamFinder teamFinder;

	@Inject
	private TeamAddCommandHandler teamAddCommandHandler;

	@Inject
	private TeamDeleteCommandHandler teamDeleteCommandHandler;

	@Inject
	private TeamUpdateCommandHandler teamUpdateCommandHandler;

	@POST
	@Path("find/{workplaceId}")
	public List<TeamDto> findByWorkPlace(@PathParam("workplaceId") String workplaceId) {
		return this.teamFinder.getTeamByWorkPlace(workplaceId);
	}

	@POST
	@Path("add")
	public void add(TeamCommand command) {
		this.teamAddCommandHandler.handle(command);
	}

	@POST
	@Path("update")
	public void update(TeamCommand command) {
		this.teamUpdateCommandHandler.handle(command);
	}

	@POST
	@Path("delete")
	public void delete(TeamCommand command) {
		this.teamDeleteCommandHandler.handle(command);
	}

}
