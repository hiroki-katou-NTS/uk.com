package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.rank;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRank;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.EmployeeRankService;
import nts.uk.ctx.at.schedule.dom.employeeinfo.rank.RankCode;

/**
 * 
 * @author hieult
 *
 */
@Stateless
public class InsertRankDivisionCommandHandler extends CommandHandler<RankDivisionCommand> {

	@Inject
	private EmployeeRankRepository rankRepository;

	@Override
	protected void handle(CommandHandlerContext<RankDivisionCommand> context) {
		RankDivisionCommand command = context.getCommand();
		List<String> listEmpId = command.getListEmpId();
		String empRankCd = command.getRankCd();
		RequireImpl require = new RequireImpl(rankRepository);
		// 1 登録する(社員ID, ランクコード) AtomTask
		// 社員ランクを登録する
		for (String empId : listEmpId) {
			AtomTask persist = EmployeeRankService.insert(require, empId, new RankCode(empRankCd));
			transaction.execute(() -> {
				persist.run();
			});
		}

	}

	@AllArgsConstructor

	private class RequireImpl implements EmployeeRankService.Require {

		private final EmployeeRankRepository empRankRepository;

		@Override
		public boolean exists(String sID) {
			return empRankRepository.exists(sID);
		}

		@Override
		public void insert(EmployeeRank employeeRank) {
			this.empRankRepository.insert(employeeRank);
		}

		@Override
		public void update(EmployeeRank employeeRank) {
			this.empRankRepository.update(employeeRank);

		}

	}

}
