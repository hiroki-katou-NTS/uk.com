package nts.uk.ctx.at.schedule.pubimp.shift.businesscalendar.specificdate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.service.IWorkplaceSpecificDateSettingService;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.service.SpecificDateItemOutput;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingExport;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingPub;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class WpSpecificDateSettingPubImpl implements WpSpecificDateSettingPub {
	
	@Inject
	private IWorkplaceSpecificDateSettingService workplaceSpecificDateSettingService;

	@Override
	public WpSpecificDateSettingExport workplaceSpecificDateSettingService(String companyID, String workPlaceID,
			GeneralDate date) {
		SpecificDateItemOutput dateItemOutput = workplaceSpecificDateSettingService.workplaceSpecificDateSettingService(companyID, workPlaceID, date);
		return new WpSpecificDateSettingExport(dateItemOutput.getDate(), dateItemOutput.getNumberList());
	}

}
