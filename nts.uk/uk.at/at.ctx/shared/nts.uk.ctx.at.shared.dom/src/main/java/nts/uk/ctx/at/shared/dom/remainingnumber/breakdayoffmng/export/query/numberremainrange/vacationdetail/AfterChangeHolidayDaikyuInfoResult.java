package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.vacationdetail;

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
 *         変更後の代休休出情報
 */
@AllArgsConstructor
@Data
public class AfterChangeHolidayDaikyuInfoResult {

	// 明細一覧
	private final VacationDetails vacationDetail;

	// 紐付け一覧
	private final SeqVacationAssociationInfoList seqVacInfoList;

	// 紐付けされている代休の未相殺数を更新する
	public void updateUnoffsetsAssociSubstVacation() {

		// 代休のみの逐次発生の休暇明細を取得
		List<AccumulationAbsenceDetail> lstDigest = vacationDetail.getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.DIGESTION).collect(Collectors.toList());

		// loop
		for (AccumulationAbsenceDetail detail : lstDigest) {
			{

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

				double day = detail.getNumberOccurren().getDay().v();
				if (!seqVacAssoci.isEmpty()) {
					day -= seqVacAssoci.stream().mapToDouble(x -> x.getDayNumberUsed().v()).sum();
				}
				// 処理中の未消化数を更新する
				detail.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(day < 0 ? 0 : day));
				// 処理中の逐次発生の休暇を代休の処理後一覧に追加する
			}
		}

	}

	// 紐付けされている休出の未相殺数を更新する
	public void updateUnoffsetAssociVacation() {

		// 休出のみの逐次発生の休暇明細を取得
		List<AccumulationAbsenceDetail> lstDigest = vacationDetail.getLstAcctAbsenDetail().stream()
				.filter(x -> x.getOccurrentClass() == OccurrenceDigClass.OCCURRENCE).collect(Collectors.toList());

		// loop
		for (AccumulationAbsenceDetail detail : lstDigest) {
			if (detail.getDateOccur().isUnknownDate()) {
				continue;
			}
			// 紐付けデータを取得する
			List<SeqVacationAssociationInfo> seqVacAssoci = seqVacInfoList.getSeqVacInfoList().stream()
					.filter(x -> detail.getDateOccur().getDayoffDate().get().equals(x.getOutbreakDay()))
					.collect(Collectors.toList());
			if (seqVacAssoci.isEmpty()) {
				continue;
			}

			double day = detail.getNumberOccurren().getDay().v();
			if (!seqVacAssoci.isEmpty()) {
				day -= seqVacAssoci.stream().mapToDouble(x -> x.getDayNumberUsed().v()).sum();
			}

			// 処理中の未消化数を更新する
			detail.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(day < 0 ? 0 : day));

		}
	}
}
