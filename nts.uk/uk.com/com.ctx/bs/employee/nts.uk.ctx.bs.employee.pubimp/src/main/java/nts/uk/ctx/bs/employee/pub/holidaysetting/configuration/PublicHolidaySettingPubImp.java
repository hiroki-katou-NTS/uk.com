/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pub.holidaysetting.configuration;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PublicHolidaySettingPubImp.
 */
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
	public PublicHolidaySettingDto FindPublicHolidaySetting() {
		String companyId = AppContexts.user().companyId();
		PublicHolidaySettingDto dto = new PublicHolidaySettingDto();
		
		Optional<PublicHolidaySetting> optPubHDSet = this.pubHdSetRepo.findByCID(companyId);
		if (optPubHDSet.isPresent()) {
			PublicHolidaySetting pubHDSet = optPubHDSet.get();
			dto.setCompanyId(pubHDSet.getCompanyID());
			dto.setIsManageComPublicHd(pubHDSet.isManageComPublicHd() == true ? MANAGE : NOT_MANAGE);
		} else {
			dto = null;
		}
		return dto;
	}
}
