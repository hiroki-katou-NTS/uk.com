/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.optionalitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.optionalitem.calculationformula.CalcFormulaSaveCommand;
import nts.uk.ctx.at.record.app.command.optionalitem.calculationformula.CalcFormulaSaveCommandHandler;
import nts.uk.ctx.at.record.app.find.optionalitem.calculationformula.CalcFormulaDto;
import nts.uk.ctx.at.record.app.find.optionalitem.calculationformula.CalcFormulaFinder;

/**
 * The Class CalcFormulaWs.
 */
@Path("ctx/at/record/optionalitem/formula")
@Produces("application/json")
public class CalcFormulaWs extends WebService {

	/** The finder. */
	@Inject
	private CalcFormulaFinder finder;

	/** The handler. */
	@Inject
	CalcFormulaSaveCommandHandler handler;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<CalcFormulaDto> findAll() {
		return finder.findAll();
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(CalcFormulaSaveCommand command) {
		this.handler.handle(command);
	}
}
