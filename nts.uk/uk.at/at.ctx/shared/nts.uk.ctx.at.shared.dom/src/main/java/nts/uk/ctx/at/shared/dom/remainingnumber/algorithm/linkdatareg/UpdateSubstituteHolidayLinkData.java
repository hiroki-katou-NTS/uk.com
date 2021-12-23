package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail.AfterChangeHolidayDaikyuInfoResult;
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
	public static AtomTask updateProcess(Require require, String sid, List<GeneralDate> lstDate,
			List<InterimDayOffMng> lstDayoff, List<InterimBreakMng> lstBreakoff) {
		
		val updateNumberUnoff = UpdateNumberUnoffDaikyuProcess.processDaikyu(require, sid, lstDate, lstDayoff, lstBreakoff);
		// $変更後の代休休出情報
		AfterChangeHolidayDaikyuInfoResult afterResult = updateNumberUnoff.getAfterResult();

		//＄暫定休出
		List<InterimBreakMng> kyusyutsu = updateNumberUnoff.getKyusyutsu();

		//＄暫定代休 
		List<InterimDayOffMng> daikyu = updateNumberUnoff.getDaikyu();

		// ＄紐付け情報
		val linkCouple = afterResult.getSeqVacInfoList().getSeqVacInfoList().stream()
				.map(x -> new LeaveComDayOffManagement(sid, x)).collect(Collectors.toList());
		
		// $暫定休出管理を削除する年月日一覧
		List<GeneralDate> lstKyusyutsu = kyusyutsu.stream().map(x -> x.getYmd()).filter(x -> !lstDate.contains(x)).collect(Collectors.toList());
		lstKyusyutsu.addAll(lstDate);

		// $暫定代休管理を削除する年月日一覧
		List<GeneralDate> lstDaikyu = daikyu.stream().map(x -> x.getYmd()).filter(x -> !lstDate.contains(x)).collect(Collectors.toList());
		lstDaikyu.addAll(lstDate);
		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		return AtomTask.of(() -> {
			require.deleteDayoffLinkWithPeriod(sid, period);
			require.insertDayOffLinkList(linkCouple);
			require.deleteBreakoffWithDateList(sid, lstKyusyutsu);
			require.insertBreakoffMngList(kyusyutsu);
			require.deleteDayoffWithDateList(sid, lstDaikyu);
			require.insertDayoffList(daikyu);
		});
	}

	public static interface Require extends UpdateNumberUnoffDaikyuProcess.Require {

		// [R-1] 休出代休紐付け管理を削除する
		// LeaveComDayOffManaRepository.deleteWithPeriod
		void deleteDayoffLinkWithPeriod(String sid, DatePeriod period);

		// [R-2] 休出代休紐付け管理を登録する
		// LeaveComDayOffManaRepository.insertList
		void insertDayOffLinkList(List<LeaveComDayOffManagement> lstDomain);

		// [R-3] 暫定休出管理を削除する
		// InterimBreakDayOffMngRepository.deleteBreakoffWithPeriod
		void deleteBreakoffWithDateList(String sid, List<GeneralDate> lstDate);

		// [R-4] 暫定休出管理を登録する
		// InterimBreakDayOffMngRepository.insertList
		void insertBreakoffMngList(List<InterimBreakMng> lstDomain);

		// [R-5] 暫定代休管理を削除する
		void deleteDayoffWithDateList(String sid, List<GeneralDate> lstDate);

		// [R-6] 暫定代休管理を登録する
		void insertDayoffList(List<InterimDayOffMng> lstDomain);

	}

}
