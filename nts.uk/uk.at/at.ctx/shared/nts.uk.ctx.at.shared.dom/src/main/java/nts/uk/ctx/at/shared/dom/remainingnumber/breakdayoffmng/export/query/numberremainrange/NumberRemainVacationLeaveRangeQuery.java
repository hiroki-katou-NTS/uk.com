package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.RemainUnDigestedDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.TotalRemainUndigestNumber.RemainUndigestResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.CalcNumCarryAtBeginMonthFromDaikyu;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.GetSequentialVacationDetailDaikyu;
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
		// 逐次発生の休暇明細一覧を取得
		val sequentialVacaDetail = GetSequentialVacationDetailDaikyu.process(require, inputParam.getCid(),
				inputParam.getSid(), inputParam.getDateData(), inputParam.getFixManaDataMonth(),
				inputParam.getInterimMng(), inputParam.getProcessDate(), inputParam.getOptBeforeResult());
		List<AccumulationAbsenceDetail> lstAccTemp = sequentialVacaDetail.getLstAcctAbsenDetail();

		// 代休、休出から月初の繰越数を計算
		val calcNumCarry = CalcNumCarryAtBeginMonthFromDaikyu.calculate(require, inputParam.getCid(), inputParam.getSid(),
				inputParam.getDateData(), sequentialVacaDetail, inputParam.isMode());
		result.setCarryoverDay(new ReserveLeaveRemainingDayNumber(calcNumCarry.getCarryForwardDays()));
		result.setCarryoverTime(new RemainingMinutes(calcNumCarry.getCarryForwardTime()));

		// 「休出代休明細」をソートする(sort 「休出代休明細」)
		lstAccTemp.sort(new AccumulationAbsenceDetailComparator());

		// 代休と休出の相殺処理
		Pair<Optional<DayOffError>, List<SeqVacationAssociationInfo>> lstSeqVacation = OffsetProcessing.process(require,
				inputParam.getCid(), inputParam.getSid(), inputParam.getDateData().end(), lstAccTemp);
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

		//時間管理の設定に従って、outputの値を補正する
		CorrectOutputAccordTimeMagSetting.correct(require, inputParam.getCid(), result);
		// エラーチェック
		CheckErrorDuringHoliday.check(result);
		lstSeqVacation.getLeft().ifPresent(x -> result.getDayOffErrors().add(x));

		result.setVacationDetails(new VacationDetails(lstAccTemp));
		// 次の集計期間の開始日を計算する
		result.setNextDay(Finally.of(inputParam.getDateData().end().addDays(1)));
		return result;
	}

	public static interface Require extends GetSequentialVacationDetailDaikyu.Require,
			CalcNumCarryAtBeginMonthFromDaikyu.Require, OffsetProcessing.Require, CorrectOutputAccordTimeMagSetting.Require {

	}

}
