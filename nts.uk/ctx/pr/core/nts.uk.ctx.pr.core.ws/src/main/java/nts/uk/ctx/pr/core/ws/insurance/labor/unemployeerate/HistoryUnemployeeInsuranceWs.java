/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.labor.unemployeerate;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.HistoryUnemployeeInsuranceDto;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.HistoryUnemployeeInsuranceFindInDto;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.find.HistoryUnemployeeInsuranceFinder;

/**
 * The Class HistoryUnemployeeInsuranceWs.
 */
@Path("pr/insurance/labor/unemployeerate/history")
@Produces("application/json")
public class HistoryUnemployeeInsuranceWs extends WebService {

	/** The find. */
	@Inject
	private HistoryUnemployeeInsuranceFinder find;

	/**
	 * Find all history.
	 *
	 * @return the list
	 */
	// get History UnemployeeInsuranceRate
	@POST
	@Path("findall/{companyCode}")
	public List<HistoryUnemployeeInsuranceDto> findAll(@PathParam("companyCode") String companyCode) {
		return find.findAll(companyCode);
	}

	/**
	 * Find history.
	 *
	 * @param historyId
	 *            the history id
	 * @return the history unemployee insurance dto
	 */
	@POST
	@Path("find")
	public HistoryUnemployeeInsuranceDto findHistory(
			HistoryUnemployeeInsuranceFindInDto historyUnemployeeInsuranceFindInDto) {
		return find.find(historyUnemployeeInsuranceFindInDto.getCompanyCode(),
				historyUnemployeeInsuranceFindInDto.getHistoryId());
	}
}
