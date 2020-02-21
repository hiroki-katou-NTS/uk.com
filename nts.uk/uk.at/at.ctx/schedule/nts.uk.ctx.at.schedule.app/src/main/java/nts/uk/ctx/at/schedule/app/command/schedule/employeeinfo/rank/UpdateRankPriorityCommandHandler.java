package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * ランクマスタの優先順を変更する
 * 
 * @author sonnh1
 *
 */
@Stateless
public class UpdateRankPriorityCommandHandler extends CommandHandler<ListRankCodeCommand> {

	@Inject
	private RankRepository rankRepo;

	@Override
	protected void handle(CommandHandlerContext<ListRankCodeCommand> context) {
		String companyId = AppContexts.user().companyId();
		List<String> rankCodeCommands = context.getCommand().listRankCode;
		// 1. get(ログイン会社ID)
		Optional<RankPriority> optRankPriority = this.rankRepo.getRankPriority(companyId);

		if (!optRankPriority.isPresent()) {
			throw new RuntimeException("DATA OF RANK IS ERRORED!");
		}
		
		RankPriority rankPriority = optRankPriority.get();
		// 2. 更新する(OrderedList<ランクコード>)
		rankPriority.update(rankCodeCommands.stream().map(x -> new RankCode(x)).collect(Collectors.toList()));
		// 3. persist()
		this.rankRepo.updateRankPriority(rankPriority);
	}

}
