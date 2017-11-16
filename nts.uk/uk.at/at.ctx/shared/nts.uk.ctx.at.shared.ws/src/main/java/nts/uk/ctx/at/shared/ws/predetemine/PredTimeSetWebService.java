/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.predetemine;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.pred.PredFinder;
import nts.uk.ctx.at.shared.app.find.pred.dto.PredDto;

/**
 * The Class PredTimeSetWebService.
 */
@Path("at/shared/pred")
@Produces("application/json")
@Stateless
public class PredTimeSetWebService extends WebService {

	/** The pred finder. */
	@Inject 
	private PredFinder predFinder;
	
	/**
	 * Find by code.
	 *
	 * @param workTimeCode the work time code
	 * @return the pred dto
	 */
	@POST
	@Path("findByCode/{workTimeCode}")
	public PredDto findByCode(@PathParam("workTimeCode") String workTimeCode){
		return this.predFinder.findByCode(workTimeCode);
	}
}
