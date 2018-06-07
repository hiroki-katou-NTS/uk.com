package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
@Setter
@NoArgsConstructor
public class NextHolidayGrantDate {
	/**
	 * 付与年月日
	 */
	private GeneralDate grantDate;

	public NextHolidayGrantDate(GeneralDate grantDate) {
		super();
		this.grantDate = grantDate;
	}
	
	
}
