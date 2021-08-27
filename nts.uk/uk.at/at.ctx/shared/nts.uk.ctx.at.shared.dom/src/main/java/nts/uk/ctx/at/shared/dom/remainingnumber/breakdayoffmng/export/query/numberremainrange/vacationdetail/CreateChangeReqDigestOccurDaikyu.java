package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.ChangeRequestInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.CreateChangeReqDigestOccurOutput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail.RequestChangeDigestOccr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.breakinfo.FixedManagementDataMonth;

/**
 * @author thanh_nx
 *
 *         消化発生の変更要求作成
 */
public class CreateChangeReqDigestOccurDaikyu {

	/**
	 * @param dateData         期間
	 * @param fixManaDataMonth 追加用確定データ
	 * @param interimMng       上書き暫定データ
	 * @param processDate      上書き期間
	 * @param optBeforeResult  前回集計結果
	 */
	public static CreateChangeReqDigestOccurOutput create(DatePeriod dateData,
			FixedManagementDataMonth fixManaDataMonth, List<InterimRemain> interimMng, Optional<DatePeriod> processDate,
			Optional<SubstituteHolidayAggrResult> optBeforeResult) {

		// 上書き期間を変更要求に追加
		ChangeRequestInPeriod changeOccr = new ChangeRequestInPeriod(new VacationDetails(new ArrayList<>()),
				new VacationDetails(new ArrayList<>()), new VacationDetails(new ArrayList<>()),
				processDate);
		ChangeRequestInPeriod changeDigest = new ChangeRequestInPeriod(new VacationDetails(new ArrayList<>()),
				new VacationDetails(new ArrayList<>()), new VacationDetails(new ArrayList<>()),
				processDate);

		// パラメータ「前回代休の集計結果」をチェックする (Check param 「前回代休の集計結果」)
		if (optBeforeResult.isPresent() && (optBeforeResult.get().getNextDay().isPresent()
				&& optBeforeResult.get().getNextDay().get().equals(dateData.start()))) {
			// 前回集計結果の逐次発生の休暇明細から消化を取得
			changeDigest.getOverwriteConfirmData().getLstAcctAbsenDetail()
					.addAll(optBeforeResult.get().getVacationDetails().getLstAcctAbsenDetail().stream()
							.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
							.collect(Collectors.toList()));

			// 前回集計結果の逐次発生の休暇明細から発生を取得
			changeOccr.getOverwriteConfirmData().getLstAcctAbsenDetail()
					.addAll(optBeforeResult.get().getVacationDetails().getLstAcctAbsenDetail().stream()
							.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
							.collect(Collectors.toList()));

		}

		// 追加確定データを消化の変更要求に追加する
		changeDigest.getAddConfirmData().getLstAcctAbsenDetail().addAll(fixManaDataMonth.getCompenDayOffMagData()
				.stream().map(x -> x.convertSeqVacationState()).collect(Collectors.toList()));

		// 上書き暫定データの代休を消化の変更要求に追加する
		changeDigest.getOverwriteTemporaryData().getLstAcctAbsenDetail()
				.addAll(interimMng.stream().filter(x -> x.getRemainType() == RemainType.SUBHOLIDAY)
						.map(x -> ((InterimDayOffMng) x).convertSeqVacationState()).collect(Collectors.toList()));

		// 上書き暫定データの休出を発生の変更要求に追加する
		changeOccr.getOverwriteTemporaryData().getLstAcctAbsenDetail()
				.addAll(interimMng.stream().filter(x -> x.getRemainType() == RemainType.BREAK)
						.map(x -> ((InterimBreakMng) x).convertUnoffset()).collect(Collectors.toList()));
		// 期間での変更要求を作成する
		return new CreateChangeReqDigestOccurOutput(
				RequestChangeDigestOccr.create(changeDigest.getAddConfirmData(), changeDigest.getOverwriteConfirmData(),
						changeDigest.getOverwriteTemporaryData(), processDate),
				RequestChangeDigestOccr.create(changeOccr.getAddConfirmData(), changeOccr.getOverwriteConfirmData(),
						changeOccr.getOverwriteTemporaryData(), processDate));
	}
}
