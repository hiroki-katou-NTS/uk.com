/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.AccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.HistoryAccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.InsuBizRateItemDto;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeAddCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeAddCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeDeleteCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeDeleteCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeUpdateCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.command.LaborInsuranceOfficeUpdateCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeFindInDto;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeFindOutDto;
import nts.uk.ctx.pr.core.app.insurance.labor.find.LaborInsuranceOfficeFinder;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeGetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class LaborInsuranceOfficeWs.
 */
@Path("ctx/pr/core/insurance/labor")
@Produces("application/json")
public class LaborInsuranceOfficeWs extends WebService {

	/** The find. */
	@Inject
	private LaborInsuranceOfficeFinder find;

	/** The add. */
	@Inject
	private LaborInsuranceOfficeAddCommandHandler add;

	/** The update. */
	@Inject
	private LaborInsuranceOfficeUpdateCommandHandler update;

	/** The delete. */
	@Inject
	private LaborInsuranceOfficeDeleteCommandHandler delete;

	/**
	 * Find all history.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall/{companyCode}")
	public List<LaborInsuranceOfficeFindOutDto> findAll(@PathParam("companyCode") String companyCode) {
		return find.findAll(companyCode);
	}

	/**
	 * Find history.
	 *
	 * @param historyId
	 *            the history id
	 * @return the history accident insurance rate dto
	 */
	@POST
	@Path("findLaborInsuranceOffice")
	public LaborInsuranceOfficeDto findByCode(LaborInsuranceOfficeFindInDto laborInsuranceOfficeFindInDto) {
		return find.findById(laborInsuranceOfficeFindInDto.getCode(), laborInsuranceOfficeFindInDto.getCompanyCode());
	}

	/**
	 * Adds the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("add")
	public void add(LaborInsuranceOfficeAddCommand command) {
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
	public void update(LaborInsuranceOfficeUpdateCommand command) {
		this.update.handle(command);
	}

	/**
	 * Delete.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("delete")
	public void delete(LaborInsuranceOfficeDeleteCommand command) {
		this.delete.handle(command);
	}

}
