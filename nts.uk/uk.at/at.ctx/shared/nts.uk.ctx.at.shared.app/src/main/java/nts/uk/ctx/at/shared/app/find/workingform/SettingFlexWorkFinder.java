/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workingform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workingform.SettingFlexWork;
import nts.uk.ctx.at.shared.dom.workingform.SettingFlexWorkRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SettingFlexWorkFinder.
 */
@Stateless
public class SettingFlexWorkFinder {

	/** The repo. */
	@Inject
	private SettingFlexWorkRepository repo;

	/**
	 * Find.
	 *
	 * @return the setting flex work dto
	 */
	public SettingFlexWorkDto find() {
		SettingFlexWork domain = this.repo.find(AppContexts.user().companyId());
		return SettingFlexWorkDto.builder().flexWorkManaging(domain.isFlexWorkManaging()).build();
	}
}
