/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.workrule.shiftmaster;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.Ksm015bStartPageDto;
import nts.uk.ctx.at.shared.app.find.workrule.shiftmaster.ShiftMasterScreenQueryFinder;

/**
 * The Class ShiftMasterWs.
 */
/**
 * @author anhdt
 *
 */
@Path("ctx/at/shared/workrule/shiftmaster")
@Produces("application/json")
public class ShiftMasterWs {
	
	@Inject
	private ShiftMasterScreenQueryFinder finder;
	
	@POST
	@Path("startPage")
	public Ksm015bStartPageDto findAll(){
		return this.finder.startBScreen();
	}
	
}
