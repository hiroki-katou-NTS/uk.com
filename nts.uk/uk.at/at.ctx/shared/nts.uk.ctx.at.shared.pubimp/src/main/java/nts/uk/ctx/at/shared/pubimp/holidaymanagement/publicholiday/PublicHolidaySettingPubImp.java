/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.pubimp.holidaymanagement.publicholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.pub.holidaymanagement.publicholiday.PublicHolidaySettingDto;
import nts.uk.ctx.at.shared.pub.holidaymanagement.publicholiday.PublicHolidaySettingPub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PublicHolidaySettingPubImp.
 */
@Stateless
public class PublicHolidaySettingPubImp implements PublicHolidaySettingPub{

	/** The pub hd set repo. */
	@Inject
	private PublicHolidaySettingRepository pubHdSetRepo;
	
	private static final Integer NOT_MANAGE = 0;
	private static final Integer MANAGE = 1;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.pub.holidaysetting.configuration.PublicHolidaySettingPub#FindPublicHolidaySetting()
	 */
	@Override
	public Optional<PublicHolidaySettingDto> FindPublicHolidaySetting() {
		String companyId = AppContexts.user().companyId();
		PublicHolidaySettingDto dto = new PublicHolidaySettingDto();
		
		Optional<PublicHolidaySetting> optPubHDSet = this.pubHdSetRepo.get(companyId);
		if (optPubHDSet.isPresent()) {
			PublicHolidaySetting pubHDSet = optPubHDSet.get();
			dto.setCompanyId(pubHDSet.getCompanyID());
			dto.setIsManageComPublicHd(pubHDSet.getIsManagePublicHoliday());
			return Optional.of(dto);
		} 
		return Optional.empty();
	}
}
