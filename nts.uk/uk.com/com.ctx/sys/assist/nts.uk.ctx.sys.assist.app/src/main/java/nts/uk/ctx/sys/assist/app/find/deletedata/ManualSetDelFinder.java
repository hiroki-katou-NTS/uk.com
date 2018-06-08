/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.app.find.deletedata;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManualSetDeletionRepository;

/**
 * @author hiep.th
 *
 */

/**
 * The Class ManualSettingFinder.
 */
@Stateless
public class ManualSetDelFinder {

	/** The repository. */
	@Inject
	private ManualSetDeletionRepository repository;

	/**
	 * Find.
	 *
	 * @return the Manual Setting dto
	 */
	public ManualSetDelDto findManualSetDelById(String delId) {
		
		Optional<ManualSetDeletion> optManualSetting = this.repository.getManualSetDeletionById(delId);
		if (optManualSetting.isPresent()) {
			return ManualSetDelDto.fromDomain(optManualSetting.get());
		}
		
		return null;
	}

}
