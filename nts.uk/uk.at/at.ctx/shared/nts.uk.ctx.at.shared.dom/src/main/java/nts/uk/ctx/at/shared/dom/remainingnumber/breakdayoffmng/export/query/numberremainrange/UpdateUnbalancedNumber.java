package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.vacation.algorithm.TimeLapseVacationSetting;

/**
 * @author ThanhNX
 *
 *         未相殺数を更新
 */
public class UpdateUnbalancedNumber {

	private UpdateUnbalancedNumber() {
	};

	public static void updateUnbalanced(TimeLapseVacationSetting timeLap, AccumulationAbsenceDetail accdigest,
			AccumulationAbsenceDetail occur, TypeOffsetJudgment typeJudgment) {

		// 代休の場合
		if (typeJudgment == TypeOffsetJudgment.REAMAIN) {
			if ((timeLap.getManagerTimeCate().isPresent() && timeLap.getManagerTimeCate().get()
					&& accdigest.getUnbalanceNumber().getDay().v() > occur.getUnbalanceNumber().getDay().v())) {

				// 「逐次発生の休暇明細」(消化)．未相殺数-= 「逐次発生の休暇明細」(発生)．未相殺数
				accdigest.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(
						accdigest.getUnbalanceNumber().getDay().v() - occur.getUnbalanceNumber().getDay().v()));

				// 「逐次発生の休暇明細」(発生)．未相殺数 = 0
				occur.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(0d));
			}

			if (timeLap.getManagerTimeCate().isPresent() && !timeLap.getManagerTimeCate().get()
					&& (accdigest.getUnbalanceNumber().getTime().isPresent()
							&& occur.getUnbalanceNumber().getTime().isPresent() && accdigest.getUnbalanceNumber()
									.getTime().get().v() > occur.getUnbalanceNumber().getTime().get().v())) {

				// 「逐次発生の休暇明細」(発生)．未相殺数 -= 「逐次発生の休暇明細」(消化)．未相殺数
				occur.getUnbalanceNumber()
						.setTime(Optional.of(new AttendanceTime(occur.getUnbalanceNumber().getTime().get().v()
								- accdigest.getUnbalanceNumber().getTime().get().v())));

				// 「逐次発生の休暇明細」(消化)．未相殺数 = 0
				accdigest.getUnbalanceNumber().setTime(Optional.empty());
			}
		} else {
			// 振出の場合
			// 逐次発生の休暇で相殺できる日数の大小チェックする
			if (accdigest.getUnbalanceNumber().getDay().v() > occur.getUnbalanceNumber().getDay().v()) {
				// 「逐次発生の休暇明細」(消化)．未相殺数-= 「逐次発生の休暇明細」(発生)．未相殺数
				accdigest.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(
						accdigest.getUnbalanceNumber().getDay().v() - occur.getUnbalanceNumber().getDay().v()));
				// 「逐次発生の休暇明細」(発生)．未相殺数 = 0
				occur.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(0d));

			} else {
				// 「逐次発生の休暇明細」(発生)．未相殺数 -= 「逐次発生の休暇明細」(消化)．未相殺数
				// 「逐次発生の休暇明細」(消化)．未相殺数 = 0
				accdigest.getUnbalanceNumber().setTime(Optional.empty());
			}
		}

	}

}
