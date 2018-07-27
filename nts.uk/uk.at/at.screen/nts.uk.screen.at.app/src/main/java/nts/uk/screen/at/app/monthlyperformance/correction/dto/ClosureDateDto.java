package nts.uk.screen.at.app.monthlyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClosureDateDto {
	
	// 締め日 . 日
	private int closeDay;
	
	// 締め日. 末日とする
	// 0 : false, 1 : true
	private int lastDayOfMonth;

}
