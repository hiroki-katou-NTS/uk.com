package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;

/**
 * @author ThanhNX
 *
 *         逐次発生の休暇明細一覧
 */
@AllArgsConstructor
@Getter
public class VacationDetails {

	// 休暇リスト
	private List<AccumulationAbsenceDetail> lstAcctAbsenDetail;

	// 日付不明ではない発生の一覧を取得
	public List<AccumulationAbsenceDetail> getOccurrenceNotDateUnknown() {
		return lstAcctAbsenDetail.stream().filter(
				x -> !x.getDateOccur().isUnknownDate() && x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE)
				.collect(Collectors.toList());

	}

	// 日付不明ではない消化の一覧を取得
	public List<AccumulationAbsenceDetail> getDigestNotDateUnknown() {
		return lstAcctAbsenDetail.stream()
				.filter(x -> !x.getDateOccur().isUnknownDate() && x.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
				.collect(Collectors.toList());

	}

	// [3] 追加する
	public void addDetail(AccumulationAbsenceDetail detail) {
		this.lstAcctAbsenDetail.add(detail);
	}

	// [4]追加する
	public void addDetail(List<AccumulationAbsenceDetail> details) {
		this.lstAcctAbsenDetail.addAll(details);
	}

	// [5] 該当日の発生数を取得する
	public double getNumberOccCorrespDay(GeneralDate correspDay) {
		return getVacStateForAppDay(correspDay).map(x -> x.getNumberOccurren().getDay().v()).orElse(0.0);
	}

	// [6] 該当日の使用数を取得する
	public double getNumberDigestCorrespDay(GeneralDate correspDay) {
		return getVacStateForAppDay(correspDay).map(x -> x.getUnbalanceNumber().getDay().v()).orElse(0.0);
	}

	// [1] 該当する日の休暇明細を取得
	private Optional<AccumulationAbsenceDetail> getVacStateForAppDay(GeneralDate correspDay) {

		return this.lstAcctAbsenDetail.stream().filter(x -> x.getDateOccur().getDayoffDate().isPresent()
				&& x.getDateOccur().getDayoffDate().get().equals(correspDay)).findFirst();
	}
}
