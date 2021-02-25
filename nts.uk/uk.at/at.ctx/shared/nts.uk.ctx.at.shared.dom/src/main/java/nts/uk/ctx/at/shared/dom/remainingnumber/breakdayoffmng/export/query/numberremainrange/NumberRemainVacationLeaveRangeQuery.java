package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.CarryForwardDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.RemainUnDigestedDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TotalRemainUndigestNumber.RemainUndigestResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * @author ThanhNX
 * 
 *         RequestList203: 期間内の休出代休残数を取得する
 * 
 *         refactor and create common process
 */
public class NumberRemainVacationLeaveRangeQuery {

	private NumberRemainVacationLeaveRangeQuery() {
	};

	// RequestList203: 期間内の休出代休残数を取得する
	// Refactor
	public static SubstituteHolidayAggrResult getBreakDayOffMngInPeriod(Require require,
			BreakDayOffRemainMngRefactParam inputParam) {

		SubstituteHolidayAggrResult result = new SubstituteHolidayAggrResult();
		CarryForwardDayTimes calcCarryForwardDays = new CarryForwardDayTimes(0.0, 0);

		// パラメータ「前回代休の集計結果」をチェックする (Check param 「前回代休の集計結果」)
		List<AccumulationAbsenceDetail> lstAccTemp = new ArrayList<>();
		if (!inputParam.getOptBeforeResult().isPresent()
				|| (inputParam.getOptBeforeResult().get().getNextDay().isPresent() && !inputParam.getOptBeforeResult()
						.get().getNextDay().get().equals(inputParam.getDateData().start()))) {
			// 月初時点の情報を整える
			calcCarryForwardDays = AcquisitionRemainNumAtStartCount.acquisition(require, inputParam.getCid(),
					inputParam.getSid(), inputParam.getDateData().start(), inputParam.getDateData().end(),
					inputParam.isMode(), lstAccTemp, inputParam.getFixManaDataMonth());
			result.setCarryoverDay(new ReserveLeaveRemainingDayNumber(calcCarryForwardDays.getCarryForwardDays()));
			result.setCarryoverTime(new RemainingMinutes(calcCarryForwardDays.getCarryForwardTime()));
		} else {
			// 繰越日数」と「繰越時間」に前回の修正結果の残数を格納
			SubstituteHolidayAggrResult beforeResult = inputParam.getOptBeforeResult().get();
			// 「繰越日数」と「繰越時間」に前回の修正結果の残数を格納
			if (beforeResult.getNextDay().isPresent()
					&& beforeResult.getNextDay().get().equals(inputParam.getDateData().start())) {
				calcCarryForwardDays.setCarryForwardDays(beforeResult.getCarryoverDay().v());
				calcCarryForwardDays.setCarryForwardTime(beforeResult.getCarryoverTime().v());
				// result.setVacationDetails(beforeResult.getVacationDetails());
				lstAccTemp.addAll(beforeResult.getVacationDetails().getLstAcctAbsenDetail());
				result.setCarryoverDay(new ReserveLeaveRemainingDayNumber(calcCarryForwardDays.getCarryForwardDays()));
				result.setCarryoverTime(new RemainingMinutes(calcCarryForwardDays.getCarryForwardTime()));

			}
		}
		

		// 今から処理が必要な代休、休出を全て集める
		lstAccTemp.addAll(GetTemporaryData.process(require, inputParam));

		// 「休出代休明細」をソートする(sort 「休出代休明細」)
		lstAccTemp.sort(new AccumulationAbsenceDetailComparator());

		// 代休と休出の相殺処理
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> lstSeqVacation = OffsetProcessing.process(require,
				inputParam.getCid(), inputParam.getSid(), inputParam.getScreenDisplayDate(), lstAccTemp);
		result.setLstSeqVacation(lstSeqVacation.getRight());
		// 残った分を参照して、残数と未消化を計算
		// da co xu ly o tren
		RemainUndigestResult remainUndigestResult = TotalRemainUndigestNumber.process(require, inputParam.getCid(),
				inputParam.getSid(), inputParam.getScreenDisplayDate(), lstAccTemp, inputParam.isMode());
		result.setRemainDay(new ReserveLeaveRemainingDayNumber(remainUndigestResult.getRemainingDay()));
		result.setRemainTime(new RemainingMinutes(remainUndigestResult.getRemainingTime()));
		result.setUnusedDay(new ReserveLeaveRemainingDayNumber(remainUndigestResult.getUndigestDay()));
		result.setUnusedTime(new RemainingMinutes(remainUndigestResult.getUndigestTime()));
		// 発生数、使用数を計算
		RemainUnDigestedDayTimes remainUnDigDayTime = CalcNumberOccurUses.process(lstAccTemp, inputParam.getDateData());
		result.setCalcNumberOccurUses(remainUnDigDayTime);

		// エラーチェック
		CheckErrorDuringHoliday.check(result);
		lstSeqVacation.getLeft().ifPresent(x -> result.getDayOffErrors().add(x));

		result.setVacationDetails(new VacationDetails(lstAccTemp));
		// 次の集計期間の開始日を計算する
		result.setNextDay(Finally.of(inputParam.getDateData().end().addDays(1)));
		return result;
	}

	public static interface Require
			extends AcquisitionRemainNumAtStartCount.Require, GetTemporaryData.Require, OffsetProcessing.Require {

	}

}
