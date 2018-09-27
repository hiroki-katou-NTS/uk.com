package nts.uk.ctx.at.record.ac.specificdatesetting;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.specificdatesetting.RecSpecificDateSettingAdapter;
import nts.uk.ctx.at.record.dom.adapter.specificdatesetting.RecSpecificDateSettingImport;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingExport;
import nts.uk.ctx.at.schedule.pub.shift.businesscalendar.specificdate.WpSpecificDateSettingPub;

@Stateless
public class RecSpecificDateSettingAdapterImpl implements RecSpecificDateSettingAdapter{
	
	@Inject
	private WpSpecificDateSettingPub wpSpecificDateSettingPub;

	@Override
	public RecSpecificDateSettingImport specificDateSettingService(String companyID, String workPlaceID,
			GeneralDate date) {
		WpSpecificDateSettingExport wpSpecificDateSettingExport = wpSpecificDateSettingPub.workplaceSpecificDateSettingService(companyID, workPlaceID, date);
		return new RecSpecificDateSettingImport(wpSpecificDateSettingExport.getDate(), wpSpecificDateSettingExport.getNumberList());
	}

	@Override
	public RecSpecificDateSettingImport specificDateSettingServiceByListWpl(String companyID, List<String> workPlaceID,
			GeneralDate date) {
		// TODO Auto-generated method stub
		return null;
	}

}
