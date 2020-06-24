package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SettingSubstituteHolidayProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;

/**
 * @author ThanhNX
 *
 *         6.残数と未消化数を集計する
 */
public class TotalRemainUndigestNumber {

	// 6.残数と未消化数を集計する
	public static RemainUndigestResult process(Require require, String companyId, String employeeID,
			GeneralDate baseDate, List<AccumulationAbsenceDetail> lstAccuAbsence, boolean isMode) {
		// 残日数 = 0、残時間数 = 0、未消化日数 = 0、未消化時間 = 0（初期化）
		double remainingDay = 0d, undigestDay = 0d;
		int remainingTime = 0, undigestTime = 0;

		// 10-2.代休の設定を取得する
		SubstitutionHolidayOutput substitutionHolidayOutput = SettingSubstituteHolidayProcess
				.getSettingForSubstituteHoliday(require, companyId, employeeID, baseDate);
		// 「休出代休明細」をループする
		for (AccumulationAbsenceDetail accuAbsence : lstAccuAbsence) {
			if (accuAbsence.getOccurrentClass() == OccurrenceDigClass.DIGESTION) {
				// 残日数 -= ループ中の「代休の未相殺」．未相殺日数、残時間 -= ループ中の「代休の未相殺」．未相殺時間
				remainingDay -= accuAbsence.getUnbalanceNumber().getDay().v();
				remainingTime -= accuAbsence.getUnbalanceNumber().getTime().isPresent()
						? accuAbsence.getUnbalanceNumber().getTime().get().v()
						: 0;
				continue;
			}

			if (!accuAbsence.getUnbalanceVacation().isPresent()) {
				continue;
			}

			// isMode = true => 月次
			if ((isMode && accuAbsence.getUnbalanceVacation().get().getDeadline().beforeOrEquals(baseDate))
					|| (!isMode && accuAbsence.getUnbalanceVacation().get().getDeadline().before(baseDate))) {
				if (substitutionHolidayOutput == null)
					continue;
				// 時間代休管理区分がtrue
				if (substitutionHolidayOutput.isTimeOfPeriodFlg()) {
					// 未消化時間 += ループ中の「休出の未使用」．未使用時間
					undigestTime += accuAbsence.getUnbalanceNumber().getTime().isPresent()
							? accuAbsence.getUnbalanceNumber().getTime().get().v()
							: 0;

				} else {
					// 未消化日数 += ループ中の「休出の未使用」．未使用日数
					undigestDay += accuAbsence.getUnbalanceNumber().getDay().v();
					if (accuAbsence.getUnbalanceNumber().getDay().v() == 1) {
						// 未消化時間 += ループ中の「休出の未使用」．１日相当時間
						undigestTime += accuAbsence.getUnbalanceVacation().get().getTimeOneDay().v();
						continue;
					}

					if (accuAbsence.getUnbalanceNumber().getDay().v() == 0.5d) {
						// 未消化時間 += ループ中の「休出の未使用」．半日相当時間
						undigestTime += accuAbsence.getUnbalanceVacation().get().getTimeHalfDay().v();
						continue;
					}
				}

			} else {
				remainingDay += accuAbsence.getUnbalanceNumber().getDay().v();
				remainingTime += accuAbsence.getUnbalanceNumber().getTime().isPresent()
						? accuAbsence.getUnbalanceNumber().getTime().get().v()
						: 0;
			}

		}
		return new RemainUndigestResult(remainingDay, remainingTime, undigestDay, undigestTime);
	}

	public static interface Require extends SettingSubstituteHolidayProcess.Require {

	}

	@Getter
	@AllArgsConstructor
	public static class RemainUndigestResult {

		/**
		 * 残日数
		 */
		private double remainingDay;
		/**
		 * 残時間数
		 */
		private int remainingTime;

		/**
		 * 未消化日数
		 */
		private double undigestDay;
		/**
		 * 未消化時間
		 */
		private int undigestTime;
	}
}
