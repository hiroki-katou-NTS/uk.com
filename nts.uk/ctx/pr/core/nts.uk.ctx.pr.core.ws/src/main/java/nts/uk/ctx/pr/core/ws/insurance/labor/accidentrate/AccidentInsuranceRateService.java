/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor.accidentrate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.core.app.insurance.labor.accidentrate.command.AccidentInsuranceRateAddCommand;
import nts.uk.ctx.core.app.insurance.labor.accidentrate.command.AccidentInsuranceRateAddCommandHandler;
import nts.uk.ctx.core.app.insurance.labor.accidentrate.command.AccidentInsuranceRateUpdateCommand;
import nts.uk.ctx.core.app.insurance.labor.accidentrate.command.AccidentInsuranceRateUpdateCommandHandler;

@Path("pr/insurance/labor/accidentrate")
@Produces("application/json")
public class AccidentInsuranceRateService extends WebService {

	/** The add. */
	 @Inject
	 AccidentInsuranceRateAddCommandHandler add;
	 /** The add. */
	 @Inject
	 AccidentInsuranceRateUpdateCommandHandler update;

	/**
	 * Adds the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("add")
	public void add(AccidentInsuranceRateAddCommand command) {
		 this.add.handle(command);
	}

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void update(AccidentInsuranceRateUpdateCommand command) {
		 this.update.handle(command);
	}

}
