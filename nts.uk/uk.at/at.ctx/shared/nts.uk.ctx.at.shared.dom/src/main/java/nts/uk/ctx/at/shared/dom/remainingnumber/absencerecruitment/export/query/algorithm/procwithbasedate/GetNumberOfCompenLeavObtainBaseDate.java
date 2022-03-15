package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.procwithbasedate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

/**
 * @author thanh_nx
 *
 *         基準日時点で取得可能な振休日数を取得する
 */
public class GetNumberOfCompenLeavObtainBaseDate {

	// 取得する
	public static LeaveRemainingDayNumber process(Require require, String cid, String sid, GeneralDate baseDate) {

		double remainResult = 0d;
		// Require．社員に対応締め開始日を取得する
		Optional<GeneralDate> closureStartOpt = GetClosureStartForEmployee.algorithm(require, new CacheCarrier(), sid);
		if (!closureStartOpt.isPresent())
			return new LeaveRemainingDayNumber(remainResult);

		// 期間内の振出振休を取得する
		val inputParam = new AbsRecMngInPeriodRefactParamInput(cid, sid,
				new DatePeriod(closureStartOpt.get(), baseDate), baseDate, false, false, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty(),
				new FixedManagementDataMonth());
		CompenLeaveAggrResult compenLeavResult = NumberCompensatoryLeavePeriodQuery.process(require, inputParam);

		// 基準日以降に紐づけされている発生数を計算
		LeaveRemainingDayNumber monthOcc = calcNumberOfOccurrence(require, compenLeavResult.getVacationDetails(),
				baseDate);
		// 残日数に発生数を加算
		remainResult = compenLeavResult.getRemainDay().v() + monthOcc.v();

		// 基準日以降に紐づけされている消化数を計算
		LeaveRemainingDayNumber monthDigest = calcNumberOfDigest(require, compenLeavResult.getVacationDetails(),
				baseDate);
		// 残日数に消化数を減算
		remainResult -= monthDigest.v();

		// outputの残数を返す
		return new LeaveRemainingDayNumber(remainResult);
	}

	// 基準日以降に紐づけされている発生数を計算
	private static LeaveRemainingDayNumber calcNumberOfOccurrence(Require require, VacationDetails vacationDetails,
			GeneralDate baseDate) {

		// 日付不明ではない消化の一覧を取得
		List<AccumulationAbsenceDetail> lstOccurence = vacationDetails.getDigestNotDateUnknown();

		// loop
		// 基準日以降の紐づけされてる振出日数を取得
		// 合計発生数に加算
		// 合計発生数を返す
		return lstOccurence.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.offsetOssociSwingAftRefDate(require, baseDate).v()),
						x -> new LeaveRemainingDayNumber(x)));
	}

	// 基準日以降に紐づけされている消化数を計算
	private static LeaveRemainingDayNumber calcNumberOfDigest(Require require, VacationDetails vacationDetails,
			GeneralDate baseDate) {

		// 日付不明ではない発生の一覧を取得
		List<AccumulationAbsenceDetail> lstOccurence = vacationDetails.getOccurrenceNotDateUnknown();

		// 基準日以降の紐づけされてる振休日数を取得
		// 合計消化数に加算
		// 合計消化数を返す
		return lstOccurence.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.summingDouble(x -> x.offsetDigestSwingAftRefDate(require, baseDate).v()),
						x -> new LeaveRemainingDayNumber(x)));

	}

	public static interface Require extends GetClosureStartForEmployee.RequireM1,
			NumberCompensatoryLeavePeriodQuery.Require, AccumulationAbsenceDetail.Require {

	}

}
