package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcution;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodExcutionRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTarget;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodTargetRepository;
//import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution.ExecutionAtr;
import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodexcution.ExecutionStatus;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author phongtq 任意期間集計Mgrクラス
 */
@Stateless
public class ExecuteAggrPeriodDomainServiceImpl implements ExecuteAggrPeriodDomainService {

	@Inject
	private AnyAggrPeriodRepository respsitory;

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
		Optional<AggrPeriodExcution> excutionPeriod = excutionRepo.findByAggr(companyId, excuteId);

		// ドメインモデル「任意集計期間」を取得
		Optional<AnyAggrPeriod> optionalPeriod = respsitory.findOneByCompanyIdAndFrameCode(companyId,
				excutionPeriod.get().getAggrFrameCode().v());

		// 期間の判断
		DatePeriod periodTime = optionalPeriod.get().getPeriod();

		// 正常終了 : 0
		// 中断 : 1
		AggProcessState status = AggProcessState.SUCCESS;

		int i = 0;
		val dataSetter = asyn.getDataSetter();
		dataSetter.setData("aggCreateCount", i);
		dataSetter.setData("aggCreateStatus", ExecutionStatus.PROCESSING.name);
		// dataSetter.setData("aggCreateHasError", " ");
		// 社員の件数分ループ
		List<AggrPeriodTarget> periodTargets = targetRepo.findAll(excuteId);
		// Get End Date Time Excution
		GeneralDateTime endDateTime = null;
		for (AggrPeriodTarget periodTarget : periodTargets) {
			status = aggrPeriod.checkAggrPeriod(companyId, excuteId, periodTime, asyn);
			i++;
			dataSetter.updateData("aggCreateCount", i);
			// ログ情報（実行内容の完了状態）を更新する
			targetRepo.updateExcution(periodTarget);
			if (status.value == AggProcessState.INTERRUPTION.value) {
				endDateTime = GeneralDateTime.now();
				excutionRepo.updateExe(excutionPeriod.get(), ExecutionStatus.END_OF_INTERRUPTION.value, endDateTime);
				dataSetter.updateData("aggCreateStatus", ExecutionStatus.END_OF_INTERRUPTION.name);
				asyn.finishedAsCancelled();
				return;
			}
		}

		// 状態を確認する
		List<AggrPeriodInfor> periodInforLst = periodInforRepo.findAll(excuteId);

		endDateTime = GeneralDateTime.now();
		if (periodInforLst.size() == 0) {
			excutionRepo.updateExe(excutionPeriod.get(), ExecutionStatus.DONE.value, endDateTime);
			dataSetter.updateData("aggCreateStatus", ExecutionStatus.DONE.name);
		} else {
			excutionRepo.updateExe(excutionPeriod.get(), ExecutionStatus.DONE_WITH_ERROR.value, endDateTime);
			dataSetter.updateData("aggCreateStatus", ExecutionStatus.DONE_WITH_ERROR.name);
		}

		// dataSetter.setData("aggCreateStatus", "完了");
		// dataSetter.setData("aggCreateHasError", "エラーなし");
	}
}
