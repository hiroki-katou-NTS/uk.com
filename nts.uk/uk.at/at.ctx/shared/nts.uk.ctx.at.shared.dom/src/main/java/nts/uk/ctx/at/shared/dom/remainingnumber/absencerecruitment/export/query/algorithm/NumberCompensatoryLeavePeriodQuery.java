package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsDaysRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.ResultAndError;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenSuspensionAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.AccumulationAbsenceDetailComparator;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * @author ThanhNX
 *
 *         期間内の振出振休残数を取得する
 */
public class NumberCompensatoryLeavePeriodQuery {

	private NumberCompensatoryLeavePeriodQuery() {
	};

	/**
	 * Requestlist204 期間内の振出振休残数を取得する
	 * 
	 * @return
	 */
	public static CompenLeaveAggrResult process(Require require, AbsRecMngInPeriodRefactParamInput inputParam) {

		CompenLeaveAggrResult result = new CompenLeaveAggrResult();
		// パラメータ「前回振休の集計結果」をチェックする
		ResultAndError carryForwardDays = new ResultAndError(0.0, false);
		List<AccumulationAbsenceDetail> lstAbsRec = new ArrayList<>();
		if (!inputParam.getOptBeforeResult().isPresent()
				|| (inputParam.getOptBeforeResult().get().getNextDay().isPresent() && !inputParam.getOptBeforeResult()
						.get().getNextDay().get().equals(inputParam.getDateData().start()))) {
			// 月初時点の情報を整える
			AbsDaysRemain absDaysRemain = PrepareInfoBeginOfMonth.prepare(require, inputParam.getCid(),
					inputParam.getSid(), inputParam.getDateData().start(), inputParam.getDateData().end(),
					inputParam.isMode(), lstAbsRec, inputParam.getFixManaDataMonth());
			result.setCarryoverDay(new ReserveLeaveRemainingDayNumber(absDaysRemain.getRemainDays()));
		} else {
			// 「繰越日数」に前回の修正結果の残数を格納
			CompenLeaveAggrResult beforeResult = inputParam.getOptBeforeResult().get();
			carryForwardDays.setRerultDays(beforeResult.getCarryoverDay().v());
			result.setCarryoverDay(new ReserveLeaveRemainingDayNumber(carryForwardDays.getRerultDays()));
			lstAbsRec.addAll(beforeResult.getVacationDetails().getLstAcctAbsenDetail());
		}

		// 今から処理が必要な振出振休を全て集める
		lstAbsRec.addAll(ProcessCompenSuspensionAll.process(require, inputParam));

		// 「振出振休明細」をソートする
		lstAbsRec.sort(new AccumulationAbsenceDetailComparator());

		// 振出と振休の相殺処理
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> lstSeqVacation = CompenSuspensionOffsetProcess.process(require,
				inputParam.getCid(), inputParam.getSid(), inputParam.getDateData().end(), lstAbsRec);
		result.setLstSeqVacation(lstSeqVacation.getRight());
		lstSeqVacation.getLeft().ifPresent(x -> result.getPError().add(PauseError.PREFETCH_ERROR));
		// 残数と未消化を集計する
		AbsDaysRemain absRemain = TotalRemainUndigest.process(lstAbsRec, inputParam.getScreenDisplayDate(),
				inputParam.isMode());
		result.setRemainDay(new ReserveLeaveRemainingDayNumber(absRemain.getRemainDays()));
		result.setUnusedDay(new ReserveLeaveRemainingDayNumber(absRemain.getUnDigestedDays()));

		// 発生数・使用数を計算する
		CompenSuspensionAggrResult compenSusAggr = CalcCompenNumberOccurUses.calc(lstAbsRec, inputParam.getDateData());
		result.setOccurrenceDay(new ReserveLeaveRemainingDayNumber(compenSusAggr.getSuOccurDay()));
		result.setDayUse(new ReserveLeaveRemainingDayNumber(compenSusAggr.getSuDayUse()));
		// 振休のエラーチェックをする
		if (result.getRemainDay().v() < 0) {
			result.getPError().add(PauseError.PAUSEREMAINNUMBER);
		}
		// 次の集計期間の開始日を計算する
		result.setNextDay(Finally.of(inputParam.getDateData().end().addDays(1)));
		result.setVacationDetails(new VacationDetails(lstAbsRec));
		return result;

	}

	public static interface Require extends PrepareInfoBeginOfMonth.Require, ProcessCompenSuspensionAll.Require,
			CompenSuspensionOffsetProcess.Require {

	}

}
