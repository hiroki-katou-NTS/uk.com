package nts.uk.ctx.at.schedule.app.command.shift.rank.ranksetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSet;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSetRepository;

@Stateless
public class RankSetAddCommandHandler extends CommandHandler<RankSetAddCommand> {

	@Inject
	RankSetRepository rankSetRepo;

	@Override
	protected void handle(CommandHandlerContext<RankSetAddCommand> context) {
		RankSetAddCommand rankSetAddCommand = context.getCommand();
		List<RankSetCommand> rankSetCommands = rankSetAddCommand.getRankSetCommands();
		List<String> employeeIds = rankSetCommands.stream().map(RankSetCommand::getSId).collect(Collectors.toList());
		if (employeeIds.size() > 0) {
			List<RankSet> rankSets = rankSetRepo.getListRankSet(employeeIds);
			rankSetCommands.stream().forEach((rankSet) -> {
				// if exist
				if (rankSets.stream().map(RankSet::getSId).filter(rankSet.getSId()::equals).findFirst().isPresent()) {
					// update
					if (rankSet.getRankCode() != null) {
						rankSetRepo.removeRankSet(rankSet.getSId());
						rankSetRepo.insetRankSet(RankSet.createFromJavaType(rankSet.getRankCode(), rankSet.getSId()));
						// rankSetRepo.updateRankSet(RankSet.createFromJavaType(rankSet.getRankCode(),
						// rankSet.getSId()));
					}
					// remove
					else {
						rankSetRepo.removeRankSet(rankSet.getSId());
					}
					// if non set
				} else {
					// insert
					if (rankSet.getRankCode() != null) {
						rankSetRepo.insetRankSet(RankSet.createFromJavaType(rankSet.getRankCode(), rankSet.getSId()));
					}
				}
			});
		}

	}

}
