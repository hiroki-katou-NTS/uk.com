package nts.uk.ctx.at.shared.dom.remainmana.export;


import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RemainManagementExport {
	/**
	 * 締めと残数算出対象期間を取得する
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public ClosureRemainPeriodOutputData getClosureRemainPeriod(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth);
	
	//getClosureOfMonthDesignation
	public DatePeriod getClosureOfMonthDesignation(Closure closureData, YearMonth ym);
}
