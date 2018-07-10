package nts.uk.ctx.at.record.dom.monthly.mergetable.primitive;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.ClosureStatus;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Getter
public class MonthCareHolidayRemain {
	
	/** 社員ID */
	private  String employeeId;
	/** 年月 */
	private  YearMonth yearMonth;
	/** 締めID */
	private  ClosureId closureId;
	/** 締め日付 */
	private  ClosureDate closureDate;
	/** 締め期間 */
	private DatePeriod closurePeriod;
	/** 締め処理状態 */
	private ClosureStatus closureStatus;
	private double childUsedDays;
	private double childUsedDaysBefore;
	private double childUsedDaysAfter;
	private int childUsedMinutes;
	private int childUsedMinutesBefore;
	private int childUsedMinutesAfter;
	
}
