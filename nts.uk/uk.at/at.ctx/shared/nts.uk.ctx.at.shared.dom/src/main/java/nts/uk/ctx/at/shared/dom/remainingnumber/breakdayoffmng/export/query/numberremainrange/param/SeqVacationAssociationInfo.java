package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * @author ThanhNX
 *
 *         逐次休暇の紐付け情報
 */
@Getter
@Setter
@AllArgsConstructor
public class SeqVacationAssociationInfo {

	// 発生日
	private GeneralDate outbreakDay;

	// 使用日
	private GeneralDate dateOfUse;

	// 使用日数
	private ReserveLeaveRemainingDayNumber dayNumberUsed;

	// 対象選択区分
	private TargetSelectionAtr targetSelectionAtr;

	// [1] 発生と使用の整合を取った状態を返す
	public Optional<SeqVacationAssociationInfo> consistentStateOccurrenceandUse(
			ReserveLeaveRemainingDayNumber targetDayNumber) {
		AssociationInfoResult check = checkCorrectAssociationInfo(targetDayNumber);
		if (AssociationInfoResult.NO_CORRECT == check) {
			return Optional.of(new SeqVacationAssociationInfo(this.outbreakDay, this.dateOfUse,
					new ReserveLeaveRemainingDayNumber(dayNumberUsed.v()), this.targetSelectionAtr));
		} else if (AssociationInfoResult.REDUCE_USE == check) {
			return Optional.of(changeNumberDayUse(new ReserveLeaveRemainingDayNumber(0.5)));
		} else {
			return Optional.empty();
		}

	}

	// [1] 整合性チェックを行い、紐づき情報に対しての補正方法を取得する
	private AssociationInfoResult checkCorrectAssociationInfo(ReserveLeaveRemainingDayNumber targetDayNumber) {
		if (dayNumberUsed.v() == 0.5) {
			if (0 != targetDayNumber.v()) {
				return AssociationInfoResult.NO_CORRECT;
			}
			return AssociationInfoResult.DELETE;

		} else if (dayNumberUsed.v() == 1.0) {
			if (0 == targetDayNumber.v()) {
				return AssociationInfoResult.DELETE;
			} else if (0.5 == targetDayNumber.v()) {
				return AssociationInfoResult.REDUCE_USE;
			} else
				return AssociationInfoResult.NO_CORRECT;
		} else {
			return AssociationInfoResult.NO_CORRECT;
		}
	}

	// [2] 使用日数を変更する
	private SeqVacationAssociationInfo changeNumberDayUse(ReserveLeaveRemainingDayNumber targetDayNumber) {
		return new SeqVacationAssociationInfo(this.outbreakDay, this.dateOfUse, targetDayNumber,
				this.targetSelectionAtr);
	}
}
