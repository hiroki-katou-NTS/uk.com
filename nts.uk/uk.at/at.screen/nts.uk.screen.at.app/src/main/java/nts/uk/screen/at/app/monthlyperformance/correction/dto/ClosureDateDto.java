package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Data
@AllArgsConstructor
public class ClosureDateDto {
	
	// 締め日 . 日
	private int closeDay;
	
	// 締め日. 末日とする
	// 0 : false, 1 : true
	private int lastDayOfMonth;
	
	public ClosureDate convertToClosureDateDto() {
		return new ClosureDate(this.closeDay, this.lastDayOfMonth==1?true:false);
	}
	
	public Boolean getLastDayOfMonthValue() {
		return this.lastDayOfMonth == 1;
	}
	
	public static ClosureDateDto convertToDoamin(ClosureDate domain) {
		return new ClosureDateDto(domain.getClosureDay().v(), domain.getLastDayOfMonth()?1:0);
	}

}
