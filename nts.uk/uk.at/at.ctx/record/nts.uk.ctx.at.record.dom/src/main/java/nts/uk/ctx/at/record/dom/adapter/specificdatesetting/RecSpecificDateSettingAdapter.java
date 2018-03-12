package nts.uk.ctx.at.record.dom.adapter.specificdatesetting;

import nts.arc.time.GeneralDate;

public interface RecSpecificDateSettingAdapter {

	/**
	 * 
	 * @param companyID
	 * @param workPlaceID
	 * @param date
	 * @return
	 */
	public RecSpecificDateSettingImport specificDateSettingService(String companyID, String workPlaceID, GeneralDate date);
	
}
