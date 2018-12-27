package nts.uk.ctx.at.record.dom.monthly.performance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.performance.enums.StateOfEditMonthly;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の編集状態
 * @author shuichu_ishida
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EditStateOfMonthlyPerformance extends AggregateRoot {
	
	/** 社員ID */
	private String employeeId;
	
	/** 処理年月 */
	private YearMonth ym;
	
	/** 期間 */
	private DatePeriod datePeriod;
	
	/** 勤怠項目ID */
	private int attendanceItemId;
	
	/** 締めID */
	private ClosureId closuteId;
	
	/** 締め日 */
	private ClosureDate closureDate;
	
	/** 編集状態: 月別実績の編集状態 */
	private StateOfEditMonthly stateOfEdit;
}
