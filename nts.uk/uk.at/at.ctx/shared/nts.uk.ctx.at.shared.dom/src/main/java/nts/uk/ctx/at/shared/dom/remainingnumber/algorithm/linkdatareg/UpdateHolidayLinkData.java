package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.AfterChangeHolidayInfoResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.GetModifiOutbreakDigest;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.RequestChangeDigestOccr;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
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

		// $発生の変更要求/
		val changeOccr = RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstOccr));

		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		// $変更後の振休振出情報=
		AfterChangeHolidayInfoResult afterResult = GetModifiOutbreakDigest.get(require, sid, period, changeDigest,
				changeOccr);

		// ＄紐付け情報
		val linkCouple = afterResult.getSeqVacInfoList().getSeqVacInfoList().stream()
				.map(x -> PayoutSubofHDManagement.of(sid, x)).collect(Collectors.toList());
		
		//	$変更後の発生一覧 
		List<AccumulationAbsenceDetail> kyusyutsuChange = afterResult.getVacationDetail().getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE).collect(Collectors.toList());
		//$変更後の消化一覧
		List<AccumulationAbsenceDetail> daikyuChange = afterResult.getVacationDetail().getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION).collect(Collectors.toList());

		List<InterimRecMng> furisyutsu = lstRecMng.stream().map(x -> {
			val dataTemp = kyusyutsuChange.stream().filter(y -> y.getManageId().equals(x.getRemainManaID()))
					.findFirst();
			return dataTemp.map(z -> x.updateUnoffsetNum(z)).orElse(null);
		}).collect(Collectors.toList());

		List<InterimAbsMng> furikyu = lstAbsMng.stream().map(x -> {
			val dataTemp = daikyuChange.stream().filter(y -> y.getManageId().equals(x.getRemainManaID())).findFirst();
			return dataTemp.map(z -> x.updateUnoffsetNum(z)).orElse(null);
		}).collect(Collectors.toList());
		
		return AtomTask.of(() -> {
			// [R-1] 振出振休紐付け管理を削除する
			require.deletePayoutWithPeriod(sid, period);

			// [R-2] 振出振休紐付け管理を登録する
			require.insertPayoutList(linkCouple);

			// [R-3] 暫定振出管理を削除する
			require.deleteRecMngWithDateList(sid, lstDate);

			// [R-4] 暫定振出管理を登録する
			require.insertRecMngList(furisyutsu);

			// [R-5] 暫定振休管理を削除する
			require.deleteAbsMngWithDateList(sid, lstDate);

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
