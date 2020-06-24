package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BreakDayOffRemainMngOfInPeriod {
	/**	List休出代休明細 */
	private List<BreakDayOffDetail> lstDetailData;
	/**	残日数 */
	private double remainDays;
	/**	残時間数 */
	private int remainTimes;
	/**	未消化日数 */
	private double unDigestedDays;
	/**	未消化時間 */
	private int unDigestedTimes;
	/**	発生日数 */
	private double occurrenceDays;
	/**	発生時間 */
	private int occurrenceTimes;
	/**	使用日数 */
	private double useDays;
	/**	使用時間 */
	private int useTimes;
	/**	繰越日数 */
	private double carryForwardDays;
	/**	繰越時間 */
	private int carryForwardTimes;
	/**
	 * 代休エラー
	 */
	private List<DayOffError> lstError;
	/**前回集計期間の翌日 */
	private Finally<GeneralDate> nextDay;
	
	public SubstituteHolidayAggrResult convert() {
		VacationDetails vacationDetails = new VacationDetails(
				lstDetailData.stream().map(x -> x.convert()).collect(Collectors.toList()));
		return new SubstituteHolidayAggrResult(vacationDetails,
				new ReserveLeaveRemainingDayNumber(this.getRemainDays()), new RemainingMinutes(this.getRemainTimes()),
				new ReserveLeaveRemainingDayNumber(this.getUseDays()), new RemainingMinutes(this.getUseTimes()),
				new ReserveLeaveRemainingDayNumber(this.getOccurrenceDays()),
				new RemainingMinutes(this.getOccurrenceTimes()),
				new ReserveLeaveRemainingDayNumber(this.getCarryForwardDays()),
				new RemainingMinutes(this.getCarryForwardTimes()),
				new ReserveLeaveRemainingDayNumber(this.getUnDigestedDays()),
				new RemainingMinutes(this.getUnDigestedTimes()), this.getLstError(), this.getNextDay(),
				new ArrayList<>());

	}
}
