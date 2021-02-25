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
			if (!timeLap.getManagerTimeCate().isPresent()
					|| (timeLap.getManagerTimeCate().get() && (!occur.getUnbalanceNumber().getTime().isPresent()
							|| !accdigest.getUnbalanceNumber().getTime().isPresent()))) {
				return;
			}

			if (!timeLap.getManagerTimeCate().get()
					&& accdigest.getUnbalanceNumber().getDay().v() >= occur.getUnbalanceNumber().getDay().v()) {
				updateValueAcc(accdigest, occur, false);
				updateValueAcc(accdigest, occur, true);
				return;
			}

			if (!timeLap.getManagerTimeCate().get()
					&& accdigest.getUnbalanceNumber().getDay().v() < occur.getUnbalanceNumber().getDay().v()) {
				updateValueAcc(occur, accdigest, false);
				updateValueAcc(occur, accdigest, true);
				return;
			}

			if (timeLap.getManagerTimeCate().get() && accdigest.getUnbalanceNumber().getTime().get().v() >= occur
					.getUnbalanceNumber().getTime().get().v()) {
				updateValueAcc(accdigest, occur, true);
				return;
			}

			if (timeLap.getManagerTimeCate().get() && accdigest.getUnbalanceNumber().getTime().get().v() < occur
					.getUnbalanceNumber().getTime().get().v()) {
				updateValueAcc(occur, accdigest, true);
				return;
			}
		} else {
			// 振出の場合
			// 逐次発生の休暇で相殺できる日数の大小チェックする
			if (accdigest.getUnbalanceNumber().getDay().v() > occur.getUnbalanceNumber().getDay().v()) {
				updateValueAcc(accdigest, occur, false);
				return;
			} else {
				updateValueAcc(occur, accdigest, false);
				return;
			}
		}

	}

	private static void updateValueAcc(AccumulationAbsenceDetail big, AccumulationAbsenceDetail small,
			boolean isMangerTime) {

		if (isMangerTime) {
			// 「逐次発生の休暇明細」(発生)．未相殺数 -= 「逐次発生の休暇明細」(消化)．未相殺数
			// 「逐次発生の休暇明細」(消化)．未相殺数-= 「逐次発生の休暇明細」(発生)．未相殺数
			big.getUnbalanceNumber().setTime(Optional.of(new AttendanceTime(
					big.getUnbalanceNumber().getTime().get().v() - small.getUnbalanceNumber().getTime().get().v())));

			// 「逐次発生の休暇明細」(消化)．未相殺数 = 0
			// 「逐次発生の休暇明細」(発生)．未相殺数 = 0
			small.getUnbalanceNumber().setTime(Optional.of(new AttendanceTime(0)));
		} else {
			big.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(
					big.getUnbalanceNumber().getDay().v() - small.getUnbalanceNumber().getDay().v()));
			// 「逐次発生の休暇明細」(発生)．未相殺数 = 0
			small.getUnbalanceNumber().setDay(new ManagementDataRemainUnit(0d));
		}
	}

}
