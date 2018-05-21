package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;

import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeDayoffRemain;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDay;
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
	private String closureId;
	/**	締め日 */
	private ClosureDay closureDay;
	/**	末日とする */
	private boolean lastDayis;
	/**	締め処理状態 */
	private ClosureStatus closureStatus;
	/**	開始年月日 */
	private GeneralDate startDate;
	/**	終了年月日 */
	private GeneralDate endDate;
	/**	発生日数 */
	private AttendanceDaysMonth occurrenceDays;
	/**	発生時間 */
	private TimeDayoffRemain occurrenceTimes;
	/**	使用日数 */
	private AttendanceDaysMonth useDays;
	/**	使用時間 */
	private TimeDayoffRemain useTimes;
	/**	残日数 */
	private AttendanceDaysMonth remainingDays;
	/**	残時間 */
	private TimeDayoffRemain remainingTimes;
	/**	繰越日数 */
	private AttendanceDaysMonth carryForWardDays;
	/**	繰越時間 */
	private TimeDayoffRemain carryForWordTimes;
	/**	未消化日数 */
	private AttendanceDaysMonth unUsedDays;
	/**	未消化時間 */
	private TimeDayoffRemain unUsedTimes;

}
