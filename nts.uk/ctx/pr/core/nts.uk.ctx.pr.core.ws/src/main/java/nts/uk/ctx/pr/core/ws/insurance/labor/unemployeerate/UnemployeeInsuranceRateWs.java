/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor.unemployeerate;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.UnemployeeInsuranceRateAddCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.UnemployeeInsuranceRateAddCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.UnemployeeInsuranceRateUpdateCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command.UnemployeeInsuranceRateUpdateCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.UnemployeeInsuranceFindInDto;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.UnemployeeInsuranceFinder;

// TODO: Auto-generated Javadoc
/**
 * The Class UnemployeeInsuranceRateWs.
 */
@Path("pr/insurance/labor/unemployeerate")
@Produces("application/json")
public class UnemployeeInsuranceRateWs extends WebService {

	/** The find. */
	@Inject
	private UnemployeeInsuranceFinder find;

	/** The add. */
	@Inject
	UnemployeeInsuranceRateAddCommandHandler add;

	/** The update. */
	@Inject
	UnemployeeInsuranceRateUpdateCommandHandler update;

	/**
	 * Detail history.
	 *
	 * @param historyId
	 *            the history id
	 * @return the unemployee insurance rate dto
	 */
	@POST
	@Path("detailHistory")
	public UnemployeeInsuranceRateDto detailHistory(UnemployeeInsuranceFindInDto unemployeeInsuranceFindInDto) {
		return find.findById(unemployeeInsuranceFindInDto.getCompanyCode(),
				unemployeeInsuranceFindInDto.getHistoryId());
	}

	/**
	 * Adds the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("add")
	public void add(UnemployeeInsuranceRateAddCommand command) {
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
	public void update(UnemployeeInsuranceRateUpdateCommand command) {
		this.update.handle(command);
	}

}
