package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;

/**
 * @author thanh_nx
 *
 *         消化発生の変更要求
 */
@AllArgsConstructor
@Getter
public class RequestChangeDigestOccr {

	// 区分
	private final ChangeRequestClassifi changeClassifi;

	// 年月日での変更要求
	private final Optional<ChangeRequestByDateList> dateChangeRequest;

	// 期間での変更要求
	private final Optional<ChangeRequestInPeriod> changeInPeriod;

	// [C-1] 期間での変更要求を作成する
	public static RequestChangeDigestOccr create(VacationDetails addConfirmData, VacationDetails overwriteConfirmData,
			VacationDetails overwriteTemporaryData, DatePeriod period) {
		return new RequestChangeDigestOccr(ChangeRequestClassifi.PERIOD, Optional.empty(), Optional
				.of(new ChangeRequestInPeriod(addConfirmData, overwriteConfirmData, overwriteTemporaryData, period)));
	}

	// 年月日での変更要求を作成する
	public static RequestChangeDigestOccr createChangeRequestbyDate(List<GeneralDate> lstDate, VacationDetails vacationDetails) {
		// ＄変更要求
		val changeByListDate = new ChangeRequestByDateList(new ArrayList<>());

		lstDate.forEach(date -> {
			val detailMatch = vacationDetails.getLstAcctAbsenDetail().stream()
					.filter(x -> x.getDateOccur().getDayoffDate().isPresent()
							&& x.getDateOccur().getDayoffDate().get().equals(date))
					.collect(Collectors.toList());
			changeByListDate.getChangeRequestList()
					.add(new ChangeRequestByDate(date, new VacationDetails(detailMatch)));
		});
		return new RequestChangeDigestOccr(ChangeRequestClassifi.DATE, Optional.of(changeByListDate), Optional.empty());
	}

	// [1] 変更する
	public VacationDetails change(VacationDetails overwriteConfirmData, VacationDetails overwriteTemporaryData) {
		// if ＠区分 =期間
		if (changeClassifi == ChangeRequestClassifi.PERIOD) {
			return changeInPeriod.map(x -> x.change(overwriteConfirmData, overwriteTemporaryData))
					.orElse(new VacationDetails(new ArrayList<>()));
		}
		// if ＠区分 =年月日
		return dateChangeRequest.map(x -> x.overwriteChangeReqByDate(overwriteTemporaryData))
				.orElse(new VacationDetails(new ArrayList<>()));
	}

}
