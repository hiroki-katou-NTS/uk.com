package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail;

import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfoList;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

/**
 * @author thanh_nx
 *
 *         休出振出管理データを補正する
 */
public class CorrectDaikyuFurikyuFixed {

	//補正する
	public static void correct(Require require, String sid, GeneralDate baseDate,
			VacationDetails vacationDetail, SeqVacationAssociationInfoList seqVacInfoList) {
		
		DatePeriod period = ClosureService.findClosurePeriod(require, new CacheCarrier(), sid, baseDate);
		vacationDetail.getLstAcctAbsenDetail().forEach(x -> {
			if (pastVacationDrawFixed(x, period)) {
				x.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(x.getUnbalanceNumber().getDay().v()
						- sumDigest(x.getDateOccur().getDayoffDate().get(), vacationDetail, seqVacInfoList, period)));
			}
		});
	
	}

	//過去の休出振出確定を取得する
	private static boolean pastVacationDrawFixed(AccumulationAbsenceDetail absDetail, DatePeriod period) {
		return absDetail.getDataAtr() == MngDataStatus.CONFIRMED
				&& absDetail.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE
				&& absDetail.getDateOccur().getDayoffDate().map(x -> pastDate(x, period)).orElse(false);
	}

	//過去かどうか
	private static boolean pastDate(GeneralDate date, DatePeriod period) {
		return !period.contains(date) && date.before(period.start());
	}
	
	//過去の代休振休確定残数を合計する
	private static double sumDigest(GeneralDate oocrDate, VacationDetails vacationDetai,
			SeqVacationAssociationInfoList seqVacInfoList, DatePeriod period) {
		val lstDegistDate = seqVacInfoList.getSeqVacInfoList().stream()
				.filter(x -> x.getOutbreakDay().equals(oocrDate) && !pastDate(x.getDateOfUse(), period))
				.collect(Collectors.toList());
		//代休確定を取得する
		val minusDate = vacationDetai.getDigestNotDateUnknown().stream().filter(d -> {
			return d.getDataAtr() != MngDataStatus.CONFIRMED && lstDegistDate.stream()
					.anyMatch(x -> x.getDateOfUse().equals(d.getDateOccur().getDayoffDate().get()));
		}).map(x -> x.getDateOccur().getDayoffDate().get()).collect(Collectors.toList());

		//過去の代休確定残数の合計
		return lstDegistDate.stream().filter(x -> minusDate.contains(x.getDateOfUse()))
				.mapToDouble(x -> x.getDayNumberUsed().v()).sum();
	}
	
	public static interface Require extends ClosureService.RequireM3 {

	}
}
