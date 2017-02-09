/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor.businesstype;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.junit.runners.Parameterized.Parameter;

import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.InsuranceBusinessTypeUpdateDto;
import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.command.InsuranceBusinessTypeUpdateCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.command.InsuranceBusinessTypeUpdateCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.businesstype.find.InsuranceBusinessTypeFinder;

@Path("pr/insurance/labor/businesstype")
@Produces("application/json")
public class InsuranceBusinessTypeService {

	/** The update. */
	@Inject
	InsuranceBusinessTypeUpdateCommandHandler update;

	/** The find. */
	@Inject
	InsuranceBusinessTypeFinder find;

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void update(InsuranceBusinessTypeUpdateCommand command) {
		this.update.handle(command);
	}

	@POST
	@Path("findall/{companyCode}")
	public InsuranceBusinessTypeUpdateDto findAll(@PathParam("companyCode") String companyCode) {
		return this.find.findAll(companyCode);
	}
}
