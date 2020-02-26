package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.Rank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankSymbol;
import nts.uk.shr.com.context.AppContexts;

/**
 * ランクマスタを更新する
 * 
 * @author sonnh1
 *
 */
@Stateless
public class UpdateRankCommandHandler extends CommandHandler<RankCommand> {
	
	@Inject
	private RankRepository rankRepo;

	@Override
	protected void handle(CommandHandlerContext<RankCommand> context) {
		String companyId = AppContexts.user().companyId();
		RankCommand rankCommand = context.getCommand();
		//1: get(ログイン会社ID, コード)
		Rank rank = this.rankRepo.getRank(companyId, new RankCode(rankCommand.getRankCd()));
        //2: set(記号)
		//3: persist()
		this.rankRepo.updateRank(new Rank(companyId, rank.getRankCode(), new RankSymbol(rankCommand.getRankSymbol())));
	}
}
