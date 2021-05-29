package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.procwithbasedate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.procwithbasedate.MonthVacationRemainDays;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

/**
 * @author thanh_nx
 *
 *         基準日時点で取得可能な代休日数を取得する
 */
public class GetNumberOfVacaLeavObtainBaseDate {

	// 取得する
	public static NumberConsecutiveVacation process(Require require, String cid, String sid, GeneralDate baseDate) {
		// Require．社員に対応締め開始日を取得する
		// 残日数
		double remainDayResult = 0d;

		Optional<GeneralDate> closureStartOpt = GetClosureStartForEmployee.algorithm(require, new CacheCarrier(), sid);
		if (!closureStartOpt.isPresent())
			return new NumberConsecutiveVacation(new MonthVacationRemainDays(remainDayResult), new RemainingMinutes(0));

		// 期間内の休出代休残数を取得する
		val inputParam = new BreakDayOffRemainMngRefactParam(cid, sid, new DatePeriod(closureStartOpt.get(), baseDate),
				false, baseDate, false, new ArrayList<>(), Optional.empty(), Optional.empty(), new ArrayList<>(),
				new ArrayList<>(), Optional.empty(), new FixedManagementDataMonth());
		SubstituteHolidayAggrResult subsHolResult = NumberRemainVacationLeaveRangeQuery
				.getBreakDayOffMngInPeriod(require, inputParam);

		// 基準日以降に紐づけされている発生数を計算
		val monthOcc = calcNumberOfOccurrence(require, subsHolResult.getVacationDetails(), baseDate);
		// 残数に発生数を加算
		remainDayResult = subsHolResult.getRemainDay().v() + monthOcc.v();

		// 基準日以降に紐づけされている消化数を計算
		val monthDigest = calcNumberOfDigest(require, subsHolResult.getVacationDetails(), baseDate);
		// 残日数に消化数を減算
		remainDayResult -= monthDigest.v();

		// output残数に残時間を入れる
		// outputの残数を返す
		return new NumberConsecutiveVacation(new MonthVacationRemainDays(remainDayResult),
				subsHolResult.getRemainTime());
	}

	// 基準日以降に紐づけされている発生数を計算
	private static MonthVacationRemainDays calcNumberOfOccurrence(Require require, VacationDetails vacationDetails,
			GeneralDate baseDate) {
		// 日付不明ではない消化の一覧を取得
		List<AccumulationAbsenceDetail> lstOccurence = vacationDetails.getDigestNotDateUnknown();
		// 基準日以降の紐づけされてる発生と相殺
		// 合計発生数に加算
		// 合計発生数を返す
		return lstOccurence.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.offsetHolAftRefDate(require, baseDate).v()),
						x -> new MonthVacationRemainDays(x)));

	}

	// 基準日以降に紐づけされている消化数を計算
	private static MonthVacationRemainDays calcNumberOfDigest(Require require, VacationDetails vacationDetails,
			GeneralDate baseDate) {
		// 日付不明ではない発生の一覧を取得
		List<AccumulationAbsenceDetail> lstOccurence = vacationDetails.getOccurrenceNotDateUnknown();
		// 基準日以降の紐づけされてる消化と相殺
		// 合計消化数に加算
		// 合計消化数を返す
		return lstOccurence.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.offsetSubsHolAftRefDate(require, baseDate).v()),
						x -> new MonthVacationRemainDays(x)));
	}

	public static interface Require extends GetClosureStartForEmployee.RequireM1,
			NumberRemainVacationLeaveRangeQuery.Require, AccumulationAbsenceDetail.Require {

	}
}
