package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.linkdatareg;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
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
 *         振休の未相殺数を更新する
 */
public class UpdateNumberUnoffFurikyuProcess {

	// 更新する
	public static UpdateNumberUnoffFurikyu processFurikyu(Require require, String sid, List<GeneralDate> lstDate,
			List<InterimAbsMng> lstAbsMng, List<InterimRecMng> lstRecMng) {

		// $発生の変更要求
		val changeOccr = createOccrChangeRequest(require, sid, lstDate, lstRecMng);

		// $消化の変更要求
		val changeDigest = createDigestChangeRequest(require, sid, lstDate, lstAbsMng);

		// $期間
		DatePeriod period = new DatePeriod(GeneralDate.min(), GeneralDate.max());
		AfterChangeHolidayInfoResult afterResult = GetModifiOutbreakDigest.getAndOffset(require, sid, period,
				changeDigest, changeOccr);
		// ＄暫定振出
		List<InterimRecMng> furisyutsu = updateNumberUnoffOccur(afterResult, lstRecMng);

		// 暫定振休
		List<InterimAbsMng> furikyu = updateNumberUnoffDigest(afterResult, lstAbsMng);

		return new UpdateNumberUnoffFurikyu(afterResult.getSeqVacInfoList(), furisyutsu, furikyu);

	}

	// [1] 変更要求と紐付いている暫定データを取得する(発生)
	private static List<InterimRecMng> getOccurTempDataFromAssoci(Require require, String sid,
			List<GeneralDate> lstDate) {
		// $紐付け一覧
		List<PayoutSubofHDManagement> linkData = require.getByListDate(sid, lstDate);

		return require.getRecBySidDateList(sid,
				linkData.stream().map(x -> x.getAssocialInfo().getOutbreakDay()).collect(Collectors.toList()));
	}

	// [2] 変更要求と紐付いている暫定データを取得する(消化)
	private static List<InterimAbsMng> getDigestTempDataFromAssoci(Require require, String sid,
			List<GeneralDate> lstDate) {
		// $紐付け一覧
		List<PayoutSubofHDManagement> linkData = require.getByListOccDate(sid, lstDate);

		return require.getAbsBySidDateList(sid,
				linkData.stream().map(x -> x.getAssocialInfo().getDateOfUse()).collect(Collectors.toList()));
	}

	// [3] 発生変更要求を作成する
	private static RequestChangeDigestOccr createOccrChangeRequest(Require require, String sid,
			List<GeneralDate> lstDate, List<InterimRecMng> lstRecMng) {
		// $紐付いている発生一覧
		val lstInterimRecMng = getOccurTempDataFromAssoci(require, sid, lstDate).stream()
				.filter(x -> lstRecMng.stream().noneMatch(y -> y.getYmd().equals(x.getYmd())))
				.collect(Collectors.toList());
		lstRecMng.addAll(lstInterimRecMng);
		// ＄逐次発生一覧
		val lstOccr = lstRecMng.stream().map(x -> x.convertUnoffset()).collect(Collectors.toList());
		// $発生の変更要求
		return RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstOccr));
	}

	// [4] 消化変更要求を作成する]
	private static RequestChangeDigestOccr createDigestChangeRequest(Require require, String sid,
			List<GeneralDate> lstDate, List<InterimAbsMng> lstAbsMng) {
		// $紐付いている消化一覧
		val lstInterimAbsMng = getDigestTempDataFromAssoci(require, sid, lstDate).stream()
				.filter(x -> lstAbsMng.stream().noneMatch(y -> y.getYmd().equals(x.getYmd())))
				.collect(Collectors.toList());
		lstAbsMng.addAll(lstInterimAbsMng);
		// ＄逐次消化一覧
		val lstDigest = lstAbsMng.stream().map(x -> x.convertSeqVacationState()).collect(Collectors.toList());
		// $消化の変更要求
		return RequestChangeDigestOccr.createChangeRequestbyDate(lstDate, new VacationDetails(lstDigest));

	}

	// [6] 発生一覧の未相殺数を更新する
	private static List<InterimRecMng> updateNumberUnoffOccur(AfterChangeHolidayInfoResult afterResult,
			List<InterimRecMng> lstRecMng) {
		// $変更後の発生一覧
		List<AccumulationAbsenceDetail> furisyutsuChange = afterResult.getVacationDetail().getLstAcctAbsenDetail()
				.stream().filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.collect(Collectors.toList());
		return lstRecMng.stream().map(x -> {
			val dataTemp = furisyutsuChange.stream().filter(y -> y.getManageId().equals(x.getRemainManaID()))
					.findFirst();
			return dataTemp.map(z -> x.updateUnoffsetNum(z)).orElse(null);
		}).collect(Collectors.toList());

	}

	// [7] 消化一覧の未相殺数を更新する]
	private static List<InterimAbsMng> updateNumberUnoffDigest(AfterChangeHolidayInfoResult afterResult,
			List<InterimAbsMng> lstAbsMng) {
		// $変更後の消化一覧
		List<AccumulationAbsenceDetail> furikyuChange = afterResult.getVacationDetail().getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION).collect(Collectors.toList());
		return lstAbsMng.stream().map(x -> {
			val dataTemp = furikyuChange.stream().filter(y -> y.getManageId().equals(x.getRemainManaID())).findFirst();
			return dataTemp.map(z -> x.updateUnoffsetNum(z)).orElse(null);
		}).collect(Collectors.toList());
	}

	public static interface Require extends GetModifiOutbreakDigest.Require {

		// PayoutSubofHDManaRepository
		List<PayoutSubofHDManagement> getByListDate(String sid, List<GeneralDate> lstDate);

		// PayoutSubofHDManaRepository
		List<PayoutSubofHDManagement> getByListOccDate(String sid, List<GeneralDate> lstDate);

		// InterimRecAbasMngRepository
		List<InterimAbsMng> getAbsBySidDateList(String sid, List<GeneralDate> lstDate);

		// InterimRecAbasMngRepository
		List<InterimRecMng> getRecBySidDateList(String sid, List<GeneralDate> lstDate);
	}
}
