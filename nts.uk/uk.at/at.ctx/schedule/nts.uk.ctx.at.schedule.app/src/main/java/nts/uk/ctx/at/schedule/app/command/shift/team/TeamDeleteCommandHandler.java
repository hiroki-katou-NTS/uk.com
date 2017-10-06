package nts.uk.ctx.at.schedule.app.command.shift.team;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.team.TeamRepository;

/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class TeamDeleteCommandHandler extends CommandHandler<TeamCommand> {

	@Inject
	private TeamRepository teamRepository;

	@Override
	protected void handle(CommandHandlerContext<TeamCommand> context) {
		// get command
		TeamCommand command = context.getCommand();
		// remove team
		teamRepository.removeTeam(command.getWorkPlaceId(), command.getTeamCode());
	}

}
