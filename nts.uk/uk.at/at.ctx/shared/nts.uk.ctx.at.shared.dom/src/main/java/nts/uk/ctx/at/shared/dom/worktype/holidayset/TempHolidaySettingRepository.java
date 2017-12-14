/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype.holidayset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * The Class TempHolidaySettingRepository.
 */
// Fake data class
@Stateless
public class TempHolidaySettingRepository implements HolidaySettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktype.holidayset.HolidaySettingRepository#
	 * findBy(java.lang.String)
	 */
	@Override
	public Optional<HolidaySetting> findBy(String companyId) {
		return Optional.of(new HolidaySetting("000000001", true, HolidayAtr.PUBLIC_HOLIDAY));
	}

}
