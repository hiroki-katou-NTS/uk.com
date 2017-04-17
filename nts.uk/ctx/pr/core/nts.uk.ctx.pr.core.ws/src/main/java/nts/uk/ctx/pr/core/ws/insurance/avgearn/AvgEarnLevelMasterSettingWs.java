/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.avgearn;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.limit.find.HealthAvgEarnLimitDto;
import nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.limit.find.HealthAvgEarnLimitFinder;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.limit.find.PensionAvgEarnLimitDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.limit.find.PensionAvgEarnLimitFinder;

/**
 * The Class AvgEarnLevelMasterSettingWs.
 */
@Path("ctx/pr/core/insurance/avgearnmaster")
@Produces("application/json")
public class AvgEarnLevelMasterSettingWs extends WebService {

	/** The health avg earn limit finder. */
	@Inject
	private HealthAvgEarnLimitFinder healthAvgEarnLimitFinder;

	/** The pension avg earn limit finder. */
	@Inject
	private PensionAvgEarnLimitFinder pensionAvgEarnLimitFinder;

	/**
	 * Find health avg earn limit.
	 *
	 * @return the list
	 */
	@POST
	@Path("health/find")
	public List<HealthAvgEarnLimitDto> findHealthAvgEarnLimit() {
		return healthAvgEarnLimitFinder.findAll();
	}

	/**
	 * Find pension avg earn limit.
	 *
	 * @return the list
	 */
	@POST
	@Path("pension/find")
	public List<PensionAvgEarnLimitDto> findPensionAvgEarnLimit() {
		return pensionAvgEarnLimitFinder.findAll();
	}
}
