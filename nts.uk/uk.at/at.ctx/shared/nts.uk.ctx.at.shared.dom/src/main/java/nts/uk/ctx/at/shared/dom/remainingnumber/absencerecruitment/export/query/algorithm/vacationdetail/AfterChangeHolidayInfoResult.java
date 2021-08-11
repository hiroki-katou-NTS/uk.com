package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.vacationdetail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfoList;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;

/**
 * @author thanh_nx
 *
 *         変更後の振休振出情報
 */
@AllArgsConstructor
@Data
public class AfterChangeHolidayInfoResult {

	// 明細一覧
	private final VacationDetails vacationDetail;

	// 紐付け一覧
	private final SeqVacationAssociationInfoList seqVacInfoList;

	// 紐付けされていない振休のみ取得する
	public void getVactionNotAssoci() {

		// 振休のみの逐次発生の休暇明細を取得
		List<AccumulationAbsenceDetail> lstDigest = vacationDetail.getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION).collect(Collectors.toList());

		// loop
		for (AccumulationAbsenceDetail detail : lstDigest) {
			if (detail.getDateOccur().isUnknownDate()) {
				continue;
			}
			// 紐付けデータを取得する
			List<SeqVacationAssociationInfo> seqVacAssoci = seqVacInfoList.getSeqVacInfoList().stream()
					.filter(x -> detail.getDateOccur().getDayoffDate().get().equals(x.getDateOfUse()))
					.collect(Collectors.toList());
			if (seqVacAssoci.isEmpty()) {
				continue;
			}

			double day = detail.getUnbalanceNumber().getDay().v()
					- seqVacAssoci.stream().mapToDouble(x -> x.getDayNumberUsed().v()).sum();

			// 処理中の未消化数を更新する
			detail.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(day < 0 ? 0 : day));

		}

	}

	// 紐付けされていない振出を取得する
	public void getOffsetNotAssoci() {
		// 振出のみの逐次発生の休暇明細を取得
		List<AccumulationAbsenceDetail> lstDigest = vacationDetail.getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE).collect(Collectors.toList());

		// loop
		for (AccumulationAbsenceDetail detail : lstDigest) {
			if (detail.getDateOccur().isUnknownDate()) {
				continue;
			}
			// 紐付けデータを取得する
			List<SeqVacationAssociationInfo> seqVacAssoci = seqVacInfoList.getSeqVacInfoList().stream()
					.filter(x -> detail.getDateOccur().getDayoffDate().get().equals(x.getDateOfUse()))
					.collect(Collectors.toList());
			if (seqVacAssoci.isEmpty()) {
				continue;
			}

			double day = detail.getUnbalanceNumber().getDay().v()
					- seqVacAssoci.stream().mapToDouble(x -> x.getDayNumberUsed().v()).sum();

			// 処理中の未消化数を更新する
			detail.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(day < 0 ? 0 : day));

		}
	}

}
