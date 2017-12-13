package nts.uk.ctx.at.request.ac.schedule.shift.businesscalendar.specificdate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.WpSpecificDateSettingAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.WpSpecificDateSettingImport;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingExport;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class WpSpecificDateSettingAdapterImpl implements WpSpecificDateSettingAdapter {
	
	@Inject
	private WpSpecificDateSettingPub wpSpecificDateSettingPub;

	@Override
	public WpSpecificDateSettingImport workplaceSpecificDateSettingService(String companyID, String workPlaceID,
			GeneralDate date) {
		WpSpecificDateSettingExport wpSpecificDateSettingExport = wpSpecificDateSettingPub.workplaceSpecificDateSettingService(companyID, workPlaceID, date);
		return new WpSpecificDateSettingImport(wpSpecificDateSettingExport.getDate(), wpSpecificDateSettingExport.getNumberList());
	}
	
}
