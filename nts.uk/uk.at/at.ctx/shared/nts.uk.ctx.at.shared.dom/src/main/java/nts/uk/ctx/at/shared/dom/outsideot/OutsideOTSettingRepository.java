/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.outsideot;

import java.util.Optional;

/**
 * The Interface OutsideOTSettingRepository.
 */
public interface OutsideOTSettingRepository {
	
	/**
	 * Find by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<OutsideOTSetting> findById(String companyId);
	
	/**
	 * Report by id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<OutsideOTSetting> reportById(String companyId);
	
	
	/**
	 * Save.
	 *
	 * @param domain the domain
	 */
	public void save(OutsideOTSetting domain);

}
