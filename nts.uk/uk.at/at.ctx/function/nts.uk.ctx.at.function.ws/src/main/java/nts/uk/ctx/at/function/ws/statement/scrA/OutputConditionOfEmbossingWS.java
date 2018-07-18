/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.ws.statement.scrA;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.statement.scrA.OutputConditionOfEmbossingDto;
import nts.uk.ctx.at.function.app.find.statement.scrA.OutputConditionOfEmbossingFinder;

/**
 * The Class OutputConditionOfEmbossingWS.
 */
@Path("at/function/statement")
@Produces(MediaType.APPLICATION_JSON)
public class OutputConditionOfEmbossingWS extends WebService{
	
	/** The output condition of embossing finder. */
	@Inject
	private OutputConditionOfEmbossingFinder outputConditionOfEmbossingFinder;
	
	/**
	 * Start page.
	 *
	 * @return the output condition of embossing dto
	 */
	@Path("startPage")
	@POST
	public OutputConditionOfEmbossingDto startPage(){
		return this.outputConditionOfEmbossingFinder.startScr();
	}
}
