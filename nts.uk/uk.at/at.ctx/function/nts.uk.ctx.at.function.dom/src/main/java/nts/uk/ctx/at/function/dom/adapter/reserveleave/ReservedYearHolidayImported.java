package nts.uk.ctx.at.function.dom.adapter.reserveleave;


import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

@Setter
@Getter
public class ReservedYearHolidayImported {
	/** 年月 */
	private YearMonth yearMonth;
	/** 月度使用日数 */
	private Double usedDays;
	/** 月度残日数 */
	private Double remainingDays;
	
	public ReservedYearHolidayImported(YearMonth yearMonth, Double usedDays, Double remainingDays) {
		this.yearMonth = yearMonth;
		this.usedDays = usedDays;
		this.remainingDays = remainingDays;
	}
	
	
}
