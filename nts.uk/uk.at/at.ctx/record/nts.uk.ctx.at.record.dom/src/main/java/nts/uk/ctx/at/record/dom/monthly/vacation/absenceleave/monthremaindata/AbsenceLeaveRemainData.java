package nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
/**
 * 振休月別残数データ
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceLeaveRemainData extends AggregateRoot{
	/**	社員ID */
	private String sId;
	/**	年月 */
	private YearMonth ym;
	/**	締めID */
	private int closureId;
	/**	締め日 */
	private int closureDay;	
	/**	末日とする */
	private boolean lastDayIs;
	/**	締め処理状態 */
	private ClosureStatus closureStatus;
	/**	開始年月日 */
	private GeneralDate startDate;
	/**	終了年月日 */
	private GeneralDate endDate;
	/**	発生日数 */
	private RemainDataDaysMonth occurredDay;
	/**	使用日数 */
	private RemainDataDaysMonth usedDays;
	/**	残日数 */
	private AttendanceDaysMonthToTal remainingDays;
	/**	繰越日数 */
	private AttendanceDaysMonthToTal carryforwardDays;
	/**	未消化日数 */
	private RemainDataDaysMonth unUsedDays;
}
