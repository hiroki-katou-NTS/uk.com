/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor.accidentrate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.HistoryAccidentInsuranceRateF‌inder;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.AccidentInsuranceRateHistoryFindOutDto;

@Path("pr/insurance/labor/accidentrate/history")
@Produces("application/json")
public class HistoryAccidentInsuranceRateWs extends WebService {

	/** The find. */
	@Inject
	private HistoryAccidentInsuranceRateF‌inder find;

	/**
	 * Find all history.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<AccidentInsuranceRateHistoryFindOutDto> findAll() {
		return find.findAll();
	}

	/**
	 * Find history.
	 *
	 * @param historyId
	 *            the history id
	 * @return the history accident insurance rate dto
	 */
	@POST
	@Path("find/{historyId}")
	public AccidentInsuranceRateHistoryFindOutDto find(@PathParam("historyId") String historyId) {
		return find.find(historyId);
	}

}
