package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution.ExecutionStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class ExecuteAggrPeriodDomainServiceImpl implements ExecuteAggrPeriodDomainService {

	@Inject
	private OptionalAggrPeriodRepository respsitory;

	@Inject
	private AggrPeriodExcutionRepository excutionRepo;

	@Inject
	private AggrPeriodTargetRepository targetRepo;

	@Inject
	private AggregatePeriodDomainService aggrPeriod;

	@Inject
	private AggrPeriodInforRepository periodInforRepo;

	@Override
	public <C> void excuteOptionalPeriod(String companyId, String excuteId, AsyncCommandHandlerContext<C> asyn) {

		// ドメインモデル「任意期間集計実行ログ」を取得
		Optional<AggrPeriodExcution> excutionPeriod = excutionRepo.findBy(companyId, excuteId,
				ExecutionStatus.PROCESSING.value);

		// ドメインモデル「任意集計期間」を取得
		Optional<OptionalAggrPeriod> optionalPeriod = respsitory.find(companyId,
				excutionPeriod.get().getAggrFrameCode().v());

		// 期間の判断
		DatePeriod periodTime = new DatePeriod(optionalPeriod.get().getStartDate(), optionalPeriod.get().getEndDate());

		// 正常終了 : 0
		// 中断 : 1
		AggProcessState status = AggProcessState.SUCCESS;

		// 社員の件数分ループ
		List<AggrPeriodTarget> periodTargets = targetRepo.findAll(excuteId);
		for (AggrPeriodTarget periodTarget : periodTargets) {
			status = aggrPeriod.checkAggrPeriod(companyId, periodTarget.getEmployeeId().toString(), periodTime);
			if (status.value == ExecutionStatus.STOP_EXECUTION.value) {
				asyn.finishedAsCancelled();
			}
			// ログ情報（実行内容の完了状態）を更新する
			targetRepo.updateExcution(periodTarget);
			
			// Get End Date Time Excution
			GeneralDateTime endDateTime = GeneralDateTime.now();
			
			// 状態を確認する
			if (excutionPeriod.isPresent() && periodTarget.getState().value == 0) {
				excutionRepo.updateExe(excutionPeriod.get(), 2, endDateTime);
			} else {
				periodInforRepo.findAll(excuteId);
				List<AggrPeriodInfor> periodInforLst = periodInforRepo.findAll(excuteId);
				
				if (periodInforLst.size() == 0) {
					excutionRepo.updateExe(excutionPeriod.get(), 0, endDateTime);
				} else {
					excutionRepo.updateExe(excutionPeriod.get(), 1, endDateTime);
				}
			}
		}
	}
}
