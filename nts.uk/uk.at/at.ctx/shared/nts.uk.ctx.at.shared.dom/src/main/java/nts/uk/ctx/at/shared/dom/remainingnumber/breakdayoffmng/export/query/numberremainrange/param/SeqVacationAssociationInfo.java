package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

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
}
