
package nts.uk.ctx.pr.core.dom.vacationsetting.yearvacationsetting;

import lombok.Getter;

@Getter
public class YearVacationTimeManageSetting {
	
	/** The check one day time enough. */
	private boolean checkOneDayTimeEnough;
	
	/** The time unit. */
	private YearVacationTimeUnit timeUnit;
	
	private YearVacationTimeMaxDay maxDay;
}
