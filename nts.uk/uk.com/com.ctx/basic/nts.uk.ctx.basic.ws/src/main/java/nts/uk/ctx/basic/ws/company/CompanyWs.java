/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.CompanyDto;
import nts.uk.ctx.basic.app.find.company.CompanyFinder;

/**
 * The Class CompanyWs.
 */
@Path("basic/company")
@Produces("application/json")
public class CompanyWs extends WebService{
	
	/** The finder. */
	@Inject
	private CompanyFinder finder;
	

	/**
	 * Gets the all company.
	 *
	 * @return the all company
	 */
	@POST
	@Path("getcompany")
	public CompanyDto getAllCompany(){
		return this.finder.getCompany();
	}
	
}
