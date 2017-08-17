/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.i18n.custom.IInternationalization;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.WorkplaceBWRemoveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.WorkplaceBWRemoveCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.WorkplaceBWSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.WorkplaceBWSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.WorkplaceBasicWorkFinder;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.WorkplaceBasicWorkFindDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * The Class WorkplaceBasicWorkWebService.
 */
@Path("ctx/at/schedule/shift/basicworkregister/workplacebasicwork/")
@Produces("application/json")
public class WorkplaceBasicWorkWebService extends WebService {

	/** The finder. */
	@Inject
	private WorkplaceBasicWorkFinder finder;

	/** The save. */
	@Inject
	private WorkplaceBWSaveCommandHandler save;

	/** The remove. */
	@Inject
	private WorkplaceBWRemoveCommandHandler remove;
	
	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;
	
	/** The internationalization. */
	@Inject
	private IInternationalization internationalization;

	/**
	 * Find all.
	 *
	 * @param workplaceId
	 *            the workplace id
	 * @return the workplace basic work find dto
	 */
	@POST
	@Path("find/{workplaceId}")
	public WorkplaceBasicWorkFindDto findAll(@PathParam("workplaceId") String workplaceId) {
		return this.finder.find(workplaceId);
	}
	
	/**
	 * Find setting.
	 *
	 * @return the list
	 */
	@POST
	@Path("findSetting")
	public List<String> findSetting() {
		return this.finder.findSetting();
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public List<String> save(WorkplaceBWSaveCommand command) {
		List<String> errorMessages = new ArrayList<>();
		// Check Worktype:
		command.getBasicWorkSetting().stream().forEach(item -> {
			WorkStyle workStyle = basicScheduleService.checkWorkDay(item.getWorktypeCode().v());
			if (workStyle.value == WorkStyle.ONE_DAY_REST.value) {
				int workDayDivision = item.getWorkdayDivision().value;
				switch (workDayDivision) {
				case 0:
					errorMessages.add(internationalization.getItemName("Msg_178", "KSM006_6").get());
				case 1:
					errorMessages.add(internationalization.getItemName("Msg_179", "KSM006_7").get());
				case 2:
					errorMessages.add(internationalization.getItemName("Msg_179", "KSM006_8").get());
				}
			}
		});
		// If has Errors
		if (errorMessages.size() <= 0) {
			// check pair WorkTypeCode - WorkTimeCode require
			command.getBasicWorkSetting().stream().forEach(item -> {
				this.basicScheduleService.ErrorCheckingStatus(item.getWorktypeCode().v(), item.getWorkingCode().v());
			});
			
			this.save.handle(command);
			return new ArrayList<> ();

		} else {
			// Show message
			return errorMessages;
		}
	}

	/**
	 * Delete.
	 *
	 * @param command
	 *            the command
	 */
	@Path("remove")
	@POST
	public void delete(WorkplaceBWRemoveCommand command) {
		this.remove.handle(command);
	}
}
