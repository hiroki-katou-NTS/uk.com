package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class NextHolidayGrantDateImported {
	/**
	 * 付与年月日
	 */
	private GeneralDate grantDate;

	public NextHolidayGrantDateImported(GeneralDate grantDate) {
		super();
		this.grantDate = grantDate;
	}
	
	
}
