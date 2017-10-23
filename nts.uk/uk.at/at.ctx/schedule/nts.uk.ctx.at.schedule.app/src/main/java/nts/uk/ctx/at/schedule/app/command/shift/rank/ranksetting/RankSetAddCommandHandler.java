package nts.uk.ctx.at.schedule.app.command.shift.rank.ranksetting;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSet;
import nts.uk.ctx.at.schedule.dom.shift.rank.ranksetting.RankSetRepository;

/**
 * command handler add rank setting
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class RankSetAddCommandHandler extends CommandHandler<RankSetAddCommand> {

	@Inject
	private RankSetRepository rankSetRepo;

	@Override
	protected void handle(CommandHandlerContext<RankSetAddCommand> context) {
		RankSetAddCommand rankSetAddCommand = context.getCommand();
		List<RankSetCommand> rankSetCommands = rankSetAddCommand.getRankSetCommands();
		List<String> employeeIds = rankSetCommands.stream().map(RankSetCommand::getSId).collect(Collectors.toList());
		if (CollectionUtil.isEmpty(employeeIds)) {
			return;
		}
		List<RankSet> rankSets = rankSetRepo.getListRankSet(employeeIds);
		Map<String, RankSet> mapRankSet = rankSets.stream()
				.collect(Collectors.toMap(RankSet::getSId, Function.identity()));
		rankSetCommands.stream().forEach((rankSet) -> {
			// if exist
			if (mapRankSet.containsKey(rankSet.getSId())) {
				// update
				if (rankSet.getRankCode() != null) {

					rankSetRepo.removeRankSet(rankSet.getSId());
					RankSet domain = RankSet.createFromJavaType(rankSet.getRankCode(), rankSet.getSId());
					domain.validate();
					rankSetRepo.addRankSet(domain);
				}
				// remove
				else {
					rankSetRepo.removeRankSet(rankSet.getSId());
				}
			} else {
				// insert
				if (rankSet.getRankCode() != null) {
					rankSetRepo.addRankSet(RankSet.createFromJavaType(rankSet.getRankCode(), rankSet.getSId()));
				}
			}
		});

	}

}
