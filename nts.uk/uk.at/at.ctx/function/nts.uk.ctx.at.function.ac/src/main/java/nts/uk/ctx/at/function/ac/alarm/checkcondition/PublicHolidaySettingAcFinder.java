package nts.uk.ctx.at.function.ac.alarm.checkcondition;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.adapter.PublicHolidaySettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.PublicHolidaySettingImport;
import nts.uk.ctx.bs.employee.pub.holidaysetting.configuration.PublicHolidaySettingDto;
import nts.uk.ctx.bs.employee.pub.holidaysetting.configuration.PublicHolidaySettingPub;

@Stateless
public class PublicHolidaySettingAcFinder implements PublicHolidaySettingAdapter {
	@Inject 
	private PublicHolidaySettingPub publicHolidaySettingPub;

	@Override
	public PublicHolidaySettingImport findPublicHolidaySetting() {
		PublicHolidaySettingImport data = convertToImport(this.publicHolidaySettingPub.FindPublicHolidaySetting()) ;
		return data;
	} 
	
	private PublicHolidaySettingImport convertToImport(PublicHolidaySettingDto export){
		return new PublicHolidaySettingImport(
				export.getCompanyId(),
				export.getIsManageComPublicHd().intValue()
				);
		
	}

}
