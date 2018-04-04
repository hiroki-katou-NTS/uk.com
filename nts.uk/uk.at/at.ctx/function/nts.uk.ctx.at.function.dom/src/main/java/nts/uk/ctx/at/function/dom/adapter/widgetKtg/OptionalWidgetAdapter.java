package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import nts.arc.time.GeneralDate;

public interface OptionalWidgetAdapter {

	/**
	 * Execute the algorithm "01. Display overtime indicator count"
	 * @return
	 */
	public int getNumberOT(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	
	/**
	 * Execute the algorithm "02. Display of break indication number of items"
	 * @return
	 */
	public int getNumberBreakIndication(String employeeId, GeneralDate startDate, GeneralDate endDate);
	


}
