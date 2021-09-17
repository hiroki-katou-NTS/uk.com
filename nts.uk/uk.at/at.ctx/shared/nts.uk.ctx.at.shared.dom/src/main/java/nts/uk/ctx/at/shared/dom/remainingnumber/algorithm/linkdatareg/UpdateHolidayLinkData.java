package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.AfterChangeHolidayInfoResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.GetModifiOutbreakDigest;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.RequestChangeDigestOccr;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
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

		// ＄逐次消化一覧
		val lstDigest = lstAbsMng.stream().map(x -> x.convertSeqVacationState()).collect(Collectors.toList());

		// ＄逐次発生一覧
		val lstOccr = lstRecMng.stream().map(x -> x.convertUnoffset()).collect(Collectors.toList());

		// $消化の変更要求
		val changeDigest = RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstDigest));

		// $発生の変更要求
		val changeOccr = RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstOccr));

		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		// $変更後の振休振出情報=
		AfterChangeHolidayInfoResult afterResult = GetModifiOutbreakDigest.get(require, sid, period, changeDigest,
				changeOccr);

		// ＄紐付け情報
		val linkCouple = afterResult.getSeqVacInfoList().getSeqVacInfoList().stream()
				.map(x -> PayoutSubofHDManagement.of(sid, x)).collect(Collectors.toList());

		List<InterimRecMng> furisyutsu = afterResult.getVacationDetail().getLstAcctAbsenDetail().stream()
				.filter(x -> x.getRecMng().isPresent()).map(x -> x.getRecMng().get()).collect(Collectors.toList());

		List<InterimAbsMng> furikyu = afterResult.getVacationDetail().getLstAcctAbsenDetail().stream()
				.filter(x -> x.getAbsMng().isPresent()).map(x -> x.getAbsMng().get()).collect(Collectors.toList());

		return AtomTask.of(() -> {
			// [R-1] 振出振休紐付け管理を削除する
			require.deletePayoutWithPeriod(sid, period);

			// [R-2] 振出振休紐付け管理を登録する
			require.insertPayoutList(linkCouple);

			// [R-3] 暫定振出管理を削除する
			require.deleteRecMngWithPeriod(sid, period);

			// [R-4] 暫定振出管理を登録する
			require.insertRecMngList(furisyutsu);

			// [R-5] 暫定振休管理を削除する
			require.deleteAbsMngWithPeriod(sid, period);

			// [R-6] 暫定振休管理を登録する
			require.insertAbsMngList(furikyu);
		});
	}

	public static interface Require extends GetModifiOutbreakDigest.Require {

		// [R-1] 振出振休紐付け管理を削除する
		// PayoutSubofHDManaRepository.deletePayoutWithPeriod
		void deletePayoutWithPeriod(String sid, DatePeriod period);

		// [R-2] 振出振休紐付け管理を登録する
		// PayoutSubofHDManaRepository.
		void insertPayoutList(List<PayoutSubofHDManagement> lstDomain);

		// [R-3] 暫定振出管理を削除する
		// InterimRecAbasMngRepository.deleteRecMngWithPeriod
		void deleteRecMngWithPeriod(String sid, DatePeriod period);

		// [R-4] 暫定振出管理を登録する
		// InterimRecAbasMngRepository.insertRecMngList
		void insertRecMngList(List<InterimRecMng> lstDomain);

		// [R-5] 暫定振休管理を削除する
		// InterimRecAbasMngRepository.deleteAbsMngWithPeriod
		void deleteAbsMngWithPeriod(String sid, DatePeriod period);

		// [R-6] 暫定振休管理を登録する
		// InterimRecAbasMngRepository.insertAbsMngList
		void insertAbsMngList(List<InterimAbsMng> lstDomain);

	}

}
