package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.RequestChangeDigestOccr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.AfterChangeHolidayDaikyuInfoResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.GetCompenChangeOccDigest;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

/**
 * @author thanh_nx
 *
 *         代休の紐付け状態を整合する
 */
public class UpdateNumberUnoffDaikyuProcess {

	// 代休の未相殺数を更新する
	public static UpdateNumberUnoffDaikyu processDaikyu(Require require, String sid, List<GeneralDate> lstDate,
			List<InterimDayOffMng> lstDayoff, List<InterimBreakMng> lstBreakoff) {
		// $発生の変更要求
		val changeOccr = createOccrChangeRequest(require, sid, lstDate, lstBreakoff);
		// $消化の変更要求
		val changeDigest = createDigestChangeRequest(require, sid, lstDate, lstDayoff);
		//$期間 
		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		AfterChangeHolidayDaikyuInfoResult afterResult = GetCompenChangeOccDigest.getAndOffset(require, sid, period, changeDigest, changeOccr);
		
		// ＄暫定休出
		List<InterimBreakMng> kyusyutsu = updateNumberUnoffOccur(afterResult, lstBreakoff);

		// ＄暫定代休
		List<InterimDayOffMng> daikyu = updateNumberUnoffDigest(afterResult, lstDayoff);
		
		return new UpdateNumberUnoffDaikyu(afterResult.getSeqVacInfoList(), kyusyutsu, daikyu);
	}

	// [1] 変更要求と紐付いている暫定データを取得する(発生)
	private static List<InterimBreakMng> getOccurTempDataFromAssoci(Require require, String sid,
			List<GeneralDate> lstDate) {
		// $紐付け一覧
		List<LeaveComDayOffManagement> linkData = require.getLeavByListDate(sid, lstDate);

		return require.getBreakBySidDateList(sid,
				linkData.stream().map(x -> x.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList()));
	}

	// [2] 変更要求と紐付いている暫定データを取得する(消化)
	private static List<InterimDayOffMng> getDigestTempDataFromAssoci(Require require, String sid,
			List<GeneralDate> lstDate) {
		// $紐付け一覧
		List<LeaveComDayOffManagement> linkData = require.getLeavByListOccDate(sid, lstDate);

		return require.getDayOffDateList(sid,
				linkData.stream().map(x -> x.getAssocialInfo().getDateOfUse()).collect(Collectors.toList()));
	}

	// [3] 発生変更要求を作成する
	private static RequestChangeDigestOccr createOccrChangeRequest(Require require, String sid,
			List<GeneralDate> lstDate, List<InterimBreakMng> lstBreakoff) {
		// $紐付いている発生一覧
		val lstBreakMng = getOccurTempDataFromAssoci(require, sid, lstDate).stream()
				.filter(x -> lstBreakoff.stream().noneMatch(y -> y.getYmd().equals(x.getYmd())))
				.collect(Collectors.toList());
		lstBreakoff.addAll(lstBreakMng);
		// ＄逐次発生一覧
		val lstOccr = lstBreakoff.stream().map(x -> x.convertUnoffset()).collect(Collectors.toList());
		// $発生の変更要求
		return RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstOccr));
	}

	// [4] 消化変更要求を作成する]
	private static RequestChangeDigestOccr createDigestChangeRequest(Require require, String sid,
			List<GeneralDate> lstDate, List<InterimDayOffMng> lstDayoff) {
		// $紐付いている消化一覧
		val lstDayoffMng = getDigestTempDataFromAssoci(require, sid, lstDate).stream()
				.filter(x -> lstDayoff.stream().noneMatch(y -> y.getYmd().equals(x.getYmd())))
				.collect(Collectors.toList());
		lstDayoff.addAll(lstDayoffMng);
		// ＄逐次消化一覧
		val lstDigest = lstDayoff.stream().map(x -> x.convertSeqVacationState()).collect(Collectors.toList());
		// $消化の変更要求
		return RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstDigest));

	}

	// [5] 発生一覧の未相殺数を更新する
	private static List<InterimBreakMng> updateNumberUnoffOccur(AfterChangeHolidayDaikyuInfoResult afterResult,
			List<InterimBreakMng> lstBreakoff) {
		// $変更後の発生一覧
		List<AccumulationAbsenceDetail> kyusyutsuChange = afterResult.getVacationDetail().getLstAcctAbsenDetail()
				.stream().filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.collect(Collectors.toList());
		return lstBreakoff.stream().map(x -> {
			val dataTemp = kyusyutsuChange.stream().filter(y -> y.getManageId().equals(x.getRemainManaID()))
					.findFirst();
			return dataTemp.map(z -> x.updateUnoffsetNum(z)).orElse(null);
		}).collect(Collectors.toList());

	}

	// [6] 消化一覧の未相殺数を更新する]
	private static List<InterimDayOffMng> updateNumberUnoffDigest(AfterChangeHolidayDaikyuInfoResult afterResult,
			List<InterimDayOffMng> lstDayoff) {
		// $変更後の消化一覧
		List<AccumulationAbsenceDetail> daikyuChange = afterResult.getVacationDetail().getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION).collect(Collectors.toList());
		return lstDayoff.stream().map(x -> {
			val dataTemp = daikyuChange.stream().filter(y -> y.getManageId().equals(x.getRemainManaID())).findFirst();
			return dataTemp.map(z -> x.updateUnoffsetNum(z)).orElse(null);
		}).collect(Collectors.toList());
	}

	public static interface Require extends GetCompenChangeOccDigest.Require {

		List<LeaveComDayOffManagement> getLeavByListDate(String sid, List<GeneralDate> lstDate);

		List<LeaveComDayOffManagement> getLeavByListOccDate(String sid, List<GeneralDate> lstDate);

		List<InterimBreakMng> getBreakBySidDateList(String sid, List<GeneralDate> lstDate);

		List<InterimDayOffMng> getDayOffDateList(String sid, List<GeneralDate> lstDate);
	}
}
