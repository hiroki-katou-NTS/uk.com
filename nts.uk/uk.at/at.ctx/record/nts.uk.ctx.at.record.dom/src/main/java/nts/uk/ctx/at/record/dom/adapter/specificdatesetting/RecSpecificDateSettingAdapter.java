package nts.uk.ctx.at.record.dom.adapter.specificdatesetting;

import java.util.List;

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
	
	/**
	 * reqList490
	 * @param companyID
	 * @param list workPlaceID
	 * @param date
	 * @return
	 */
	public RecSpecificDateSettingImport specificDateSettingServiceByListWpl(String companyID, List<String> workPlaceID, GeneralDate date);
	
}
