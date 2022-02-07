package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;

/**
 * @author thanh_nx
 *
 *振出振休紐付けデータを更新する
 */
public class UpdateHolidayLinkData {

	// [1] 更新する
	public static AtomTask updateProcess(Require require, String sid, List<GeneralDate> lstDate,
			List<InterimAbsMng> lstAbsMng, List<InterimRecMng> lstRecMng) {

		val updateNumberUnoff = UpdateNumberUnoffFurikyuProcess.processFurikyu(require, sid, lstDate, lstAbsMng,
				lstRecMng);
		// ＄紐付け情報
		val linkCouple = updateNumberUnoff.getSeqVacInfoList().getSeqVacInfoList().stream()
				.map(x -> PayoutSubofHDManagement.of(sid, x)).collect(Collectors.toList());

		// $暫定振出管理を削除する年月日一覧
		List<GeneralDate> lstFurisyutsu = updateNumberUnoff.getFurisyutsu().stream().map(x -> x.getYmd())
				.filter(x -> !lstDate.contains(x)).collect(Collectors.toList());
		lstFurisyutsu.addAll(lstDate);

		// $暫定振休管理を削除する年月日一覧
		List<GeneralDate> lstFurikyu = updateNumberUnoff.getFurikyu().stream().map(x -> x.getYmd())
				.filter(x -> !lstDate.contains(x)).collect(Collectors.toList());
		lstFurikyu.addAll(lstDate);

		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		return AtomTask.of(() -> {
			// [R-1] 振出振休紐付け管理を削除する
			require.deletePayoutWithPeriod(sid, period);

			// [R-2] 振出振休紐付け管理を登録する
			require.insertPayoutList(linkCouple);

			// [R-3] 暫定振出管理を削除する
			require.deleteRecMngWithDateList(sid, lstFurisyutsu);

			// [R-4] 暫定振出管理を登録する
			require.insertRecMngList(updateNumberUnoff.getFurisyutsu());

			// [R-5] 暫定振休管理を削除する
			require.deleteAbsMngWithDateList(sid, lstFurikyu);

			// [R-6] 暫定振休管理を登録する
			require.insertAbsMngList(updateNumberUnoff.getFurikyu());
		});
	}

		
	public static interface Require extends UpdateNumberUnoffFurikyuProcess.Require {

		// [R-1] 振出振休紐付け管理を削除する
		// PayoutSubofHDManaRepository.deletePayoutWithPeriod
		void deletePayoutWithPeriod(String sid, DatePeriod period);

		// [R-2] 振出振休紐付け管理を登録する
		// PayoutSubofHDManaRepository.
		void insertPayoutList(List<PayoutSubofHDManagement> lstDomain);

		// [R-3] 暫定振出管理を削除する
		// InterimRecAbasMngRepository.deleteRecMngWithPeriod
		void deleteRecMngWithDateList(String sid, List<GeneralDate> lstDate);

		// [R-4] 暫定振出管理を登録する
		// InterimRecAbasMngRepository.insertRecMngList
		void insertRecMngList(List<InterimRecMng> lstDomain);

		// [R-5] 暫定振休管理を削除する
		// InterimRecAbasMngRepository.deleteAbsMngWithPeriod
		void deleteAbsMngWithDateList(String sid, List<GeneralDate> lstDate);

		// [R-6] 暫定振休管理を登録する
		// InterimRecAbasMngRepository.insertAbsMngList
		void insertAbsMngList(List<InterimAbsMng> lstDomain);
		
	}

}
