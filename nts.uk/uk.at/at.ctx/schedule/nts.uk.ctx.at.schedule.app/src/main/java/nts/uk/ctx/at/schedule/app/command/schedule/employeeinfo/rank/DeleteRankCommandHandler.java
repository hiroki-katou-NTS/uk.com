package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * ランクマスタを削除する
 * 
 * @author sonnh1
 *
 */
@Stateless
public class DeleteRankCommandHandler extends CommandHandler<RankCommand> {

	@Inject
	private RankRepository rankRepo;

	@Override
	protected void handle(CommandHandlerContext<RankCommand> context) {
		String companyId = AppContexts.user().companyId();
		RankCommand command = context.getCommand();
		//1:  delete(会社ID,ランクコード)
		//1.1 delete
		this.rankRepo.delete(companyId, new RankCode(command.getRankCd()));
	}
}
