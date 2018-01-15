package nts.uk.ctx.at.schedule.app.command.shift.team;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.team.Team;
import nts.uk.ctx.at.schedule.dom.shift.team.TeamRepository;

/**
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class TeamAddCommandHandler extends CommandHandler<TeamCommand> {

	@Inject
	private TeamRepository teamRespository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<TeamCommand> context) {
		// get command
		TeamCommand command = context.getCommand();

		Optional<Team> team = this.teamRespository.findTeamByPK(command.getTeamCode(),command.getWorkPlaceId());
		if (team.isPresent()) {
			throw new BusinessException("Msg_3");
		}
		Team domain = Team.createFromJavaType(command.getWorkPlaceId(), command.getTeamCode(), command.getTeamName());
		// call repository add domain
		this.teamRespository.addTeam(domain);
	}

}
