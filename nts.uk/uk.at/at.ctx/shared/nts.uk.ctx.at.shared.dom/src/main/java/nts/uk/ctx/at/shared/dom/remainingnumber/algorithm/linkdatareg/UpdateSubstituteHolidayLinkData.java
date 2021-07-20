package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.RequestChangeDigestOccr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.AfterChangeHolidayDaikyuInfoResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.GetCompenChangeOccDigest;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

/**
 * @author thanh_nx
 *
 *         代休紐付けデータを更新する
 */
public class UpdateSubstituteHolidayLinkData {

	// [1] 更新する
	public static AtomTask updateProcess(Require require, String sid, DatePeriod period, List<GeneralDate> lstDate,
			List<InterimDayOffMng> lstDayoff, List<InterimBreakMng> lstBreakoff) {

		// ＄逐次消化一覧
		val lstDigest = lstDayoff.stream().map(x -> x.convertSeqVacationState()).collect(Collectors.toList());

		// ＄逐次発生一覧
		val lstOccr = lstBreakoff.stream().map(x -> x.convertUnoffset()).collect(Collectors.toList());

		// $消化の変更要求
		val changeDigest = RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstDigest));

		// $発生の変更要求
		val changeOccr = RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstOccr));

		// $変更後の代休休出情報
		AfterChangeHolidayDaikyuInfoResult afterResult = GetCompenChangeOccDigest.get(require, sid, period,
				changeDigest, changeOccr);

		// ＄紐付け情報
		val linkCouple = afterResult.getSeqVacInfoList().getSeqVacInfoList().stream()
				.map(x -> new LeaveComDayOffManagement(sid, x)).collect(Collectors.toList());

		List<InterimBreakMng> kyusyutsu = lstBreakoff.stream().map(x -> {
			if (changeOccr.getDateChangeRequest().isPresent()) {
				val detail = changeOccr.getDateChangeRequest().get().getChangeRequestList().stream()
						.flatMap(y -> y.getVacDetail().getLstAcctAbsenDetail().stream())
						.filter(y -> y.getManageId().equals(x.getId())).findFirst().orElse(null);
				return detail == null ? null : x.updateUnoffsetNum(detail);
			}
			return null;
		}).filter(x -> x != null).collect(Collectors.toList());

		List<InterimDayOffMng> daikyu = lstDayoff.stream().map(x -> {
			if (changeDigest.getDateChangeRequest().isPresent()) {
				val detail = changeDigest.getDateChangeRequest().get().getChangeRequestList().stream()
						.flatMap(y -> y.getVacDetail().getLstAcctAbsenDetail().stream())
						.filter(y -> y.getManageId().equals(x.getId())).findFirst().orElse(null);
				return detail == null ? null : x.updateUnoffsetNum(detail);
			}
			return null;
		}).filter(x -> x != null).collect(Collectors.toList());

		return AtomTask.of(() -> {
			require.deleteDayoffLinkWithPeriod(sid, period);
			require.insertDayOffLinkList(linkCouple);
			require.deleteBreakOffMngWithPeriod(sid, period);
			require.insertBreakoffMngList(kyusyutsu);
			require.deleteDayoffWithPeriod(sid, period);
			require.insertDayoffList(daikyu);
		});
	}

	public static interface Require extends GetCompenChangeOccDigest.Require {

		// [R-1] 休出代休紐付け管理を削除する
		// LeaveComDayOffManaRepository.deleteWithPeriod
		void deleteDayoffLinkWithPeriod(String sid, DatePeriod period);

		// [R-2] 休出代休紐付け管理を登録する
		// LeaveComDayOffManaRepository.insertList
		void insertDayOffLinkList(List<LeaveComDayOffManagement> lstDomain);

		// [R-3] 暫定休出管理を削除する
		// InterimBreakDayOffMngRepository.deleteBreakoffWithPeriod
		void deleteBreakOffMngWithPeriod(String sid, DatePeriod period);

		// [R-4] 暫定休出管理を登録する
		// InterimBreakDayOffMngRepository.insertList
		void insertBreakoffMngList(List<InterimBreakMng> lstDomain);

		// [R-5] 暫定代休管理を削除する
		void deleteDayoffWithPeriod(String sid, DatePeriod period);

		// [R-6] 暫定代休管理を登録する
		void insertDayoffList(List<InterimDayOffMng> lstDomain);

	}

}
