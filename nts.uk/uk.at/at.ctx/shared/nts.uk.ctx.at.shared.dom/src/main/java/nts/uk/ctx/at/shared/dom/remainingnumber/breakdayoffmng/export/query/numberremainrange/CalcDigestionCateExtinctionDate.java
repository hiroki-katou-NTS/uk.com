package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.LeaveOccurrDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;

/**
 * @author ThanhNX
 *
 *         消化区分と消滅日を計算する
 */
public class CalcDigestionCateExtinctionDate {

	private CalcDigestionCateExtinctionDate() {
	};

	// 消化区分と消滅日を計算する
	public static void calc(List<AccumulationAbsenceDetail> lstAccAbs, GeneralDate date,
			TypeOffsetJudgment typeJudgment) {

		// 「INPUT．逐次発生の休暇明細」でループ
		for (AccumulationAbsenceDetail data : lstAccAbs) {

			if (data.getOccurrentClass() == OccurrenceDigClass.DIGESTION)
				continue;

			Optional<LeaveOccurrDetail> leavOcc = occurrDetail(data, typeJudgment);
			// ループ中の「逐次発生の休暇明細（発生）」の「未相殺」をチェックする
			if (data.getUnbalanceNumber().allFieldZero()) {

				// 休暇発生明細．消化区分 ← "消化済"
				leavOcc.get().setDigestionCate(DigestionAtr.USED);
			} else {

				if (leavOcc.get().getDeadline().after(date)) {
					// 休暇発生明細．消化区分 ← "未消化"
					leavOcc.get().setDigestionCate(DigestionAtr.UNUSED);
				} else {

					// 休暇発生明細．消化区分 ← "消滅"
					leavOcc.get().setDigestionCate(DigestionAtr.EXPIRED);

					// 休暇発生明細．消滅日 ← 休暇発生明細．期限日
					// if (leavOcc.get().getExtinctionDate().isPresent()) {
					leavOcc.get().setExtinctionDate(Optional.of(leavOcc.get().getDeadline()));
					// }

				}
			}
		}

	}

	private static Optional<LeaveOccurrDetail> occurrDetail(AccumulationAbsenceDetail occur,
			TypeOffsetJudgment typeJudgment) {
		if (typeJudgment == TypeOffsetJudgment.ABSENCE) {
			return Optional.of((UnbalanceCompensation) occur);
		} else {
			return Optional.of((UnbalanceVacation) occur);
		}

	}

}
