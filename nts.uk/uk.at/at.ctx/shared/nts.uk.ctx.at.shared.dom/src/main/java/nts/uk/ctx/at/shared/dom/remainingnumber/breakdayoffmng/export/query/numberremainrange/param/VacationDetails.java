package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
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

}
