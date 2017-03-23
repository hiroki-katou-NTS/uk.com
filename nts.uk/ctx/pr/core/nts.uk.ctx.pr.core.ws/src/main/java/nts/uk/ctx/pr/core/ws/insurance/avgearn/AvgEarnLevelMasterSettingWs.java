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
import nts.uk.ctx.pr.core.app.insurance.avgearn.find.AvgEarnLevelMasterSettingDto;
import nts.uk.ctx.pr.core.app.insurance.avgearn.find.AvgEarnLevelMasterSettingFinder;

/**
 * The Class AvgEarnLevelMasterSettingWs.
 */
@Path("ctx/pr/core/insurance/avgearnmaster")
@Produces("application/json")
public class AvgEarnLevelMasterSettingWs extends WebService {
	
	/** The avg earn level master setting finder. */
	@Inject
	private AvgEarnLevelMasterSettingFinder avgEarnLevelMasterSettingFinder;

	/**
	 * Find.
	 *
	 * @return the list
	 */
	@POST
	@Path("find")
	public List<AvgEarnLevelMasterSettingDto> find() {
		return avgEarnLevelMasterSettingFinder.findAll();
	}

}
