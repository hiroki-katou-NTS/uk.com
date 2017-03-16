/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.ws.insurance;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.report.app.insurance.command.CheckListPrintSettingSaveCommand;
import nts.uk.ctx.pr.report.app.insurance.command.CheckListPrintSettingSaveCommandHandler;
import nts.uk.ctx.pr.report.app.insurance.find.ChecklistPrintSettingFinder;
import nts.uk.ctx.pr.report.app.insurance.find.dto.CheckListPrintSettingFindOutDto;

/**
 * The Class CheckListPrintSettingWs.
 */
@Path("ctx/pr/report/insurance/checklist")
@Produces("application/json")
public class CheckListPrintSettingWs extends WebService {

	/** The checklist print setting finder. */
	@Inject
	private ChecklistPrintSettingFinder checklistPrintSettingFinder;

	/** The save commmand handler. */

	@Inject
	private CheckListPrintSettingSaveCommandHandler saveCommmandHandler;

	/**
	 * Find.
	 *
	 * @return the check list print setting find out dto
	 */
	@POST
	@Path("find")
	public CheckListPrintSettingFindOutDto find() {
		CheckListPrintSettingFindOutDto dto = new CheckListPrintSettingFindOutDto();
		dto.setShowCategoryInsuranceItem(true);
		dto.setShowDetail(true);
		dto.setShowOffice(false);
		dto.setShowTotal(true);
		dto.setShowDeliveryNoticeAmount(true);
		return dto;
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(CheckListPrintSettingSaveCommand command) {
		this.saveCommmandHandler.handle(command);
	}
}
