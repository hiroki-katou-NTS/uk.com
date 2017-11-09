package nts.uk.ctx.at.request.ac.schedule.shift.businesscalendar.specificdate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.WpSpecificDateSettingAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.WpSpecificDateSettingImport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class WpSpecificDateSettingAdapterImpl implements WpSpecificDateSettingAdapter {
	
	@Inject
	private WpSpecificDateSettingAdapter wpSpecificDateSettingAdapter;

	@Override
	public WpSpecificDateSettingImport workplaceSpecificDateSettingService(String companyID, String workPlaceID,
			GeneralDate date) {
		return wpSpecificDateSettingAdapter.workplaceSpecificDateSettingService(companyID, workPlaceID, date);
	}
	
}
