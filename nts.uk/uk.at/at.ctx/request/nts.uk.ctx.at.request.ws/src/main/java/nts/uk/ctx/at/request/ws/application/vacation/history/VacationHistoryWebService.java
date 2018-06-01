/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.ws.application.vacation.history;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.command.setting.vacation.history.dto.VacationHistoryReturnDto;
import nts.uk.ctx.at.request.app.command.vacation.history.RemoveVacationHistoryCommand;
import nts.uk.ctx.at.request.app.command.vacation.history.RemoveVacationHistoryCommandHandler;
import nts.uk.ctx.at.request.app.command.vacation.history.SaveVacationHistCommandHandler;
import nts.uk.ctx.at.request.app.command.vacation.history.VacationHistoryCommand;
import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class VacationHistoryWebService.
 */
@Path("at/request/application/vacation")
@Produces("application/json")
public class VacationHistoryWebService extends WebService {

	/** The history repository. */
	@Inject
	private VacationHistoryRepository historyRepository;

	/** The save history command handler. */
	@Inject
	private SaveVacationHistCommandHandler saveHistoryCommandHandler;
	
	@Inject
	private RemoveVacationHistoryCommandHandler removeHistoryCommandHandler;

	/**
	 * Gets the history by work type.
	 *
	 * @param workTypeCode
	 *            the work type code
	 * @return the history by work type
	 */
	@POST
	@Path("getHistoryByWorkType/{workTypeCode}")
	public List<VacationHistoryReturnDto> getHistoryByWorkType(@PathParam("workTypeCode") String workTypeCode) {

		// Get companyId;
		String companyId = AppContexts.user().companyId();

		// Get WorkTypeCode List
		List<PlanVacationHistory> historyList = this.historyRepository.findByWorkTypeCode(companyId, workTypeCode);

		// convert to Dto
		return this.toDto(historyList);

	}

	/**
	 * To dto.
	 *
	 * @param historyList
	 *            the history list
	 * @return the list
	 */
	// To Dto By Domain
	private List<VacationHistoryReturnDto> toDto(List<PlanVacationHistory> historyList) {
		return historyList.stream().map(item -> {
			VacationHistoryReturnDto dto = new VacationHistoryReturnDto();
			dto.setHistoryId(item.identifier());
			dto.setStartDate(item.span().start());
			dto.setEndDate(item.span().end());
			dto.setMaxDay(item.getMaxDay().v());
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * Sets the ting history.
	 *
	 * @param command
	 *            the new ting history
	 */
	@POST
	@Path("settingHistory")
	public void settingHistory(VacationHistoryCommand command) {
		// Add VacationHistory
		this.saveHistoryCommandHandler.handle(command);
	}

	/**
	 * Removes the vacation history.
	 *
	 * @param historyId
	 *            the history id
	 * @param workTypeCode
	 *            the work type code
	 */
	@POST
	@Path("removeVacationHistory")
	public void removeVacationHistory(RemoveVacationHistoryCommand command) {
		// remove VacationHistory
		this.removeHistoryCommandHandler.handle(command);
	}
}
