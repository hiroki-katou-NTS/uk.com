/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workingform;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.find.workingform.SettingFlexWorkDto;
import nts.uk.ctx.at.shared.app.find.workingform.SettingFlexWorkFinder;

/**
 * The Class SettingFlexWorkWs.
 */
@Path("ctx/at/shared/workingform/settingflexwork")
@Produces("application/json")
public class SettingFlexWorkWs {

	/** The finder. */
	@Inject
	private SettingFlexWorkFinder finder;

	/**
	 * Find.
	 *
	 * @return the setting flex work dto
	 */
	@POST
	@Path("find")
	public SettingFlexWorkDto find() {
		return this.finder.find();
	}
}
