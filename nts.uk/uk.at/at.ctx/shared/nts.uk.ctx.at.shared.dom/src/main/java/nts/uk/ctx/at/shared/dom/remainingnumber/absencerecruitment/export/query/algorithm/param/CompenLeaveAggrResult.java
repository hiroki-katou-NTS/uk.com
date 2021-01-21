package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.PauseError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SeqVacationAssociationInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * @author ThanhNX
 *
 *         振休の集計結果
 */
@Getter
@AllArgsConstructor
@Setter
public class CompenLeaveAggrResult {

	// 振出振休明細
	private VacationDetails vacationDetails;

	// 残日数
	private ReserveLeaveRemainingDayNumber remainDay;

	// 未消化日数
	private ReserveLeaveRemainingDayNumber unusedDay;

	// 発生日数
	private ReserveLeaveRemainingDayNumber occurrenceDay;

	// 使用日数
	private ReserveLeaveRemainingDayNumber dayUse;

	// 繰越日数
	private ReserveLeaveRemainingDayNumber carryoverDay;

	// 前回集計期間の翌日
	private Finally<GeneralDate> nextDay;

	// 逐次休暇の紐付け情報
	private List<SeqVacationAssociationInfo> lstSeqVacation;

	// 振休エラー
	private List<PauseError> pError;

	public CompenLeaveAggrResult() {
		this.pError = new ArrayList<>();
		this.lstSeqVacation = new ArrayList<>();
	}

}
