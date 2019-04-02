package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClosureDateDto {

	/** The closure day. */
	// 日
	private int closureDay;

	/** The last day of month. */
	// 末日とする
	private Boolean lastDayOfMonth;

	public static ClosureDateDto fromDomain(ClosureDate domain) {
		return new ClosureDateDto(domain.getClosureDay().v(), domain.getLastDayOfMonth());
	}

}
