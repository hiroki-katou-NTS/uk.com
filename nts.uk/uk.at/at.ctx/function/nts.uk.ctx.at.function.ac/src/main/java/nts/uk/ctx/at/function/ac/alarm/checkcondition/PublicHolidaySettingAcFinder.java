package nts.uk.ctx.at.function.ac.alarm.checkcondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.PublicHolidaySettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.PublicHolidaySettingImport;
import nts.uk.ctx.at.shared.pub.holidaymanagement.publicholiday.PublicHolidaySettingDto;
import nts.uk.ctx.at.shared.pub.holidaymanagement.publicholiday.PublicHolidaySettingPub;

@Stateless
public class PublicHolidaySettingAcFinder implements PublicHolidaySettingAdapter {
    @Inject 
    private PublicHolidaySettingPub publicHolidaySettingPub;

    @Override
    public PublicHolidaySettingImport findPublicHolidaySetting() {
        Optional<PublicHolidaySettingDto> optionalSetting = this.publicHolidaySettingPub.FindPublicHolidaySetting();
        if (optionalSetting.isPresent()) {
            return convertToImport(optionalSetting.get());
        }
        return null;
    } 
    
    private PublicHolidaySettingImport convertToImport(PublicHolidaySettingDto export){
        return new PublicHolidaySettingImport(
                export.getCompanyId(),
                export.getIsManageComPublicHd().intValue()
                );
        
    }

}