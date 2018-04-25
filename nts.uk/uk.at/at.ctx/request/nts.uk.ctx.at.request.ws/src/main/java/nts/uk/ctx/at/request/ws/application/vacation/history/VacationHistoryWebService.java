/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.ws.application.vacation.history;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class VacationHistoryWebService.
 */
@Path("at/request/application/vacation")
@Produces("application/json")
public class VacationHistoryWebService extends WebService{
	
	/** The history repository. */
	@Inject
	private VacationHistoryRepository historyRepository;

	/**
	 * Gets the history by work type.
	 *
	 * @param workTypeCode the work type code
	 * @return the history by work type
	 */
	@POST
	@Path("getHistoryByWorkType/{workTypeCode}")
	public List<PlanVacationHistory> getHistoryByWorkType(@PathParam("workTypeCode") String workTypeCode) {
		
		//Get companyId;
		String companyId = AppContexts.user().companyId();
		
		// Get WorkTypeCode List
		List<PlanVacationHistory> historyList = this.historyRepository.findByWorkTypeCode(companyId, workTypeCode);
		
		return historyList;

	}
}
