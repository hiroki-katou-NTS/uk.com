package nts.uk.ctx.at.schedule.ws.shift.team.teamsetting;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.team.teamsetting.TeamSetAddCommand;
import nts.uk.ctx.at.schedule.app.command.shift.team.teamsetting.TeamSetAddCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.team.teamsetting.TeamSetDto;
import nts.uk.ctx.at.schedule.app.find.shift.team.teamsetting.TeamSetFinder;

@Path("at/schedule/shift/team/teamsetting")
@Produces(MediaType.APPLICATION_JSON)
public class TeamSetWebService extends WebService {
	@Inject
	TeamSetFinder teamSetFinder;

	@Inject
	TeamSetAddCommandHandler teamSetAddHandler;

	@POST
	@Path("findAll")
	public List<TeamSetDto> getAllTeamSet() {
		return this.teamSetFinder.getAllTeamSet();
	}

	@POST
	@Path("add")
	public void insertListTeamSet(TeamSetAddCommand command) {
		this.teamSetAddHandler.handle(command);
	}

}
