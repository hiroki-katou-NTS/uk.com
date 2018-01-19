/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.employmentfunction;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.employmentfunction.LinkPlanTimeItemFinder;


/**
 * The Class LinkPlanTimeItemWS.
 */
@Path("at/function/employmentfunction")
@Produces(MediaType.APPLICATION_JSON)
public class LinkPlanTimeItemWS extends WebService {
	
	/** The link plan time item finder. */
	@Inject
	private LinkPlanTimeItemFinder linkPlanTimeItemFinder;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Path("findAll")
	@POST
	public List<Integer> findAll(){
		return this.linkPlanTimeItemFinder.findAll();
	}
}