package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.RemainUnDigestedDayTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * @author ThanhNX
 *
 *         代休の集計結果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubstituteHolidayAggrResult {

	// 休出代休明細
	private VacationDetails vacationDetails;

	// 残日数
	private ReserveLeaveRemainingDayNumber remainDay;

	// 残時間
	private RemainingMinutes remainTime;

	// 使用日数
	private ReserveLeaveRemainingDayNumber dayUse;

	// 使用時間
	private RemainingMinutes timeUse;

	// 発生日数
	private ReserveLeaveRemainingDayNumber occurrenceDay;

	// 発生時間
	private RemainingMinutes occurrenceTime;

	// 繰越日数
	private ReserveLeaveRemainingDayNumber carryoverDay;

	// 繰越時間
	private RemainingMinutes carryoverTime;

	// 未消化日数
	private ReserveLeaveRemainingDayNumber unusedDay;

	// 未消化時間
	private RemainingMinutes unusedTime;

	// エラー情報
	private List<DayOffError> dayOffErrors;

	// 前回集計期間の翌日
	private Finally<GeneralDate> nextDay;

	// 逐次休暇の紐付け情報
	private List<SeqVacationAssociationInfo> lstSeqVacation;

	public void setCalcNumberOccurUses(RemainUnDigestedDayTimes data) {
		this.dayUse = new ReserveLeaveRemainingDayNumber(data.getUnDigestedDays());
		this.timeUse = new RemainingMinutes(data.getUnDigestedTimes());
		this.occurrenceDay = new ReserveLeaveRemainingDayNumber(data.getRemainDays());
		this.occurrenceTime = new RemainingMinutes(data.getRemainTimes());
	}

	public void removeAllDayValue() {
		this.remainDay = new ReserveLeaveRemainingDayNumber(0.0);
		this.dayUse = new ReserveLeaveRemainingDayNumber(0.0);
		this.occurrenceDay = new ReserveLeaveRemainingDayNumber(0.0);
		this.carryoverDay = new ReserveLeaveRemainingDayNumber(0.0);
		this.unusedDay = new ReserveLeaveRemainingDayNumber(0.0);
	}
	
	public void removeAllTimeValue() {
		this.remainTime = new RemainingMinutes(0);
		this.timeUse = new RemainingMinutes(0);
		this.occurrenceTime = new RemainingMinutes(0);
		this.carryoverTime = new RemainingMinutes(0);
		this.unusedTime = new RemainingMinutes(0);
	}
}
