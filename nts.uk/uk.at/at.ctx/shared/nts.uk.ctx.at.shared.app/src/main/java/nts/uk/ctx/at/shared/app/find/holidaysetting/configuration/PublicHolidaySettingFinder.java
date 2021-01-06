package nts.uk.ctx.at.shared.app.find.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author quytb
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PublicHolidaySettingFinder {

	@Inject
	private PublicHolidaySettingRepository pubHdSetRepo;
	
	public PublicHolidaySettingDto findPubHdSetting() {
		String companyId = AppContexts.user().companyId();		
		Optional<PublicHolidaySetting> holidaySetting = pubHdSetRepo.get(companyId);
		if(holidaySetting.isPresent()) {
			PublicHolidaySettingDto dto = new PublicHolidaySettingDto();
			dto.setCarryOverNumberOfPublicHdIsNegative(holidaySetting.get().getCarryOverNumberOfPublicHolidayIsNegative());
			dto.setManagePublicHoliday(holidaySetting.get().getIsManagePublicHoliday());
			dto.setPublicHdCarryOverDeadline(holidaySetting.get().getPublicHolidayCarryOverDeadline().value);
			dto.setPublicHolidayPeriod(holidaySetting.get().getPublicHolidayPeriod().value);
			return dto;
		} else {
			return null;
		}
	}
}
