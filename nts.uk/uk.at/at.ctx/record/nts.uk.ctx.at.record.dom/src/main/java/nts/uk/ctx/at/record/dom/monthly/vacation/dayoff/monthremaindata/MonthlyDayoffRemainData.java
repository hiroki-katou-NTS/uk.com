package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
/**
 * 代休月別残数データ
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyDayoffRemainData extends AggregateRoot{
	/**	社員ID */
	private String sId;
	/**年月	 */
	private YearMonth ym;
	/**締めID	 */
	private int closureId;
	/**	締め日 */
	private int closureDay;
	/**	末日とする */
	private boolean lastDayis;
	/**	締め処理状態 */
	private ClosureStatus closureStatus;
	/**	開始年月日 */
	private GeneralDate startDate;
	/**	終了年月日 */
	private GeneralDate endDate;
	/**	発生 */
	private DayOffDayAndTimes occurrenceDayTimes;	
	/**	使用 */
	private DayOffDayAndTimes useDayTimes;	
	/**	残日数, 残時間 */
	private DayOffRemainDayAndTimes remainingDayTimes;
	/**	繰越日数, 	繰越時間 */
	private DayOffRemainDayAndTimes carryForWardDayTimes;	
	/**	未消化日数, 未消化時間 */
	private DayOffDayAndTimes unUsedDayTimes;

	/**
	 * for using table merge KrcdtMonRemainMerge
	 * @param sId
	 * @param ym
	 * @param closureId
	 * @param closureDay
	 * @param lastDayis
	 * @param startDate
	 * @param endDate
	 * @param occurrenceDayTimes
	 * @param useDayTimes
	 * @param remainingDayTimes
	 * @param carryForWardDayTimes
	 * @param unUsedDayTimes
	 */
	public MonthlyDayoffRemainData(String sId, YearMonth ym, int closureId, int closureDay, boolean lastDayis,
			GeneralDate  startDate, GeneralDate endDate, DayOffDayAndTimes occurrenceDayTimes,
			DayOffDayAndTimes useDayTimes, DayOffRemainDayAndTimes remainingDayTimes,
			DayOffRemainDayAndTimes carryForWardDayTimes, DayOffDayAndTimes unUsedDayTimes) {
		this.sId = sId;
		this.closureId = closureId;
		this.ym = ym;
		this.closureDay = closureDay;
		this.lastDayis = lastDayis;
		this.startDate = startDate;
		this.endDate = endDate;
		this.occurrenceDayTimes = occurrenceDayTimes;
		this.useDayTimes = useDayTimes;
		this.remainingDayTimes = remainingDayTimes;
		this.carryForWardDayTimes = carryForWardDayTimes;
		this.unUsedDayTimes = unUsedDayTimes;
	}

}
