package nts.uk.ctx.at.schedule.app.command.shift.team.teamsetting;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.team.teamsetting.TeamSet;
import nts.uk.ctx.at.schedule.dom.shift.team.teamsetting.TeamSetRepository;

@Stateless
public class TeamSetAddCommandHandler extends CommandHandler<TeamSetAddCommand> {

	@Inject
	private TeamSetRepository teamSetRepos;

	@Override
	@Transactional
	protected void handle(CommandHandlerContext<TeamSetAddCommand> context) {
		TeamSetAddCommand command = context.getCommand();
		List<String> employeeCodes = command.getEmployeeCodes();
		String teamCode = command.getTeamCode();
		String workPlaceId = command.getWorkPlaceId();
		// add
		if (CollectionUtil.isEmpty(employeeCodes)) {
			throw new BusinessException("Msg_341");
		}
		// remove list team want added to team code
		teamSetRepos.removeListTeamSet(employeeCodes, workPlaceId);

		// remove list team by teamCode
		teamSetRepos.removeTeamSetByTeamCode(workPlaceId, teamCode);
		employeeCodes.stream().forEach(employeeCode -> {
			// add teamSet
			TeamSet domain = TeamSet.createFromJavaType(teamCode, employeeCode, workPlaceId);
			domain.validate();
			teamSetRepos.addTeamSet(domain);
		});

	}

}
