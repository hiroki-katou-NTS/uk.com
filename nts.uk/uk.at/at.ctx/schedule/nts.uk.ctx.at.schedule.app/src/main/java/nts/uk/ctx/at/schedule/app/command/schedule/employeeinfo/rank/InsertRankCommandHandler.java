package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.InsertRankService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankPriority;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankSymbol;
import nts.uk.shr.com.context.AppContexts;

/**
 * ランクマスタを登録する
 * 
 * @author sonnh1
 *
 */
@Stateless
public class InsertRankCommandHandler extends CommandHandler<RankCommand> {

	@Inject
	private RankRepository rankRepo;

	@Override
	protected void handle(CommandHandlerContext<RankCommand> context) {
		String companyId = AppContexts.user().companyId();
		RankCommand rankCommand = context.getCommand();
		RequireImpl require = new RequireImpl(rankRepo);

		//1: 新しいランクを追加する(Require, 会社ID, ランクコード, ランク記号)
		AtomTask persist = InsertRankService.insert(require, companyId, new RankCode(rankCommand.getRankCd()),
				new RankSymbol(rankCommand.getRankSymbol()));
        //2:
		transaction.execute(() -> {
			persist.run();
		});
	}

	@AllArgsConstructor
	private static class RequireImpl implements InsertRankService.Require {

		private final RankRepository rankRepo;

		@Override
		public boolean checkRankExist(String companyId, RankCode rankCd) {
			return this.rankRepo.exist(companyId, rankCd);
		}

		@Override
		public Optional<RankPriority> getRankPriority(String companyId) {
			return this.rankRepo.getRankPriority(companyId);
		}

		@Override
		public void insertRank(Rank rank, RankPriority rankPriority) {
			this.rankRepo.insert(rank, rankPriority);
		}

	}

}
