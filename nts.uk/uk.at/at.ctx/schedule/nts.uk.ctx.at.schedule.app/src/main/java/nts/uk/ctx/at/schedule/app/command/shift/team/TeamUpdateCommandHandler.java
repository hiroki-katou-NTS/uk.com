package nts.uk.ctx.at.schedule.app.command.shift.team;

import java.util.Optional;

import javax.ejb.Stateless;
/**
 * 
 * @author Trung Tran
 *
 */
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.team.Team;
import nts.uk.ctx.at.schedule.dom.shift.team.TeamRepository;

@Stateless
public class TeamUpdateCommandHandler extends CommandHandler<TeamCommand> {
	@Inject
	private TeamRepository teamRespository;

	@Override
	protected void handle(CommandHandlerContext<TeamCommand> context) {
		// get command
		TeamCommand command = context.getCommand();
		Optional<Team> team = teamRespository.findTeamByPK(command.getWorkPlaceId(), command.getTeamCode());
		if (team.isPresent()) {
			Team domain = Team.createFromJavaType(command.getWorkPlaceId(), command.getTeamCode(),
					command.getTeamName());
			domain.validate();
			teamRespository.updateTeam(domain);
		}
	}
}
