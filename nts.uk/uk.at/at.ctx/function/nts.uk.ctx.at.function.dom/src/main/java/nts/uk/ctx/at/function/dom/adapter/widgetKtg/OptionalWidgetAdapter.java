package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface OptionalWidgetAdapter {

	/**
	 * Execute the algorithm "01. Display overtime indicator count"
	 * @return
	 */
	int getNumberOT(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	
	/**
	 * Execute the algorithm "02. Display of break indication number of items"
	 * @return
	 */
	int getNumberBreakIndication(String employeeId, GeneralDate startDate, GeneralDate endDate);
	
	/*get OptionalWidget from request list 365*/
	Optional<OptionalWidgetImport> getSelectedWidget(String companyId, String topPagePartCode);
	
}
