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
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.ClassifiBWRemoveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.ClassifiBWRemoveCommandHandler;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.ClassifiBWSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.ClassifiBWSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.ClassifiBasicWorkFinder;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.ClassifiBasicWorkFindDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * The Class ClassificationBasicWorkWebService.
 */
@Path("ctx/at/schedule/shift/basicworkregister/classificationbasicwork/")
@Produces("application/json")
public class ClassificationBasicWorkWebService extends WebService {

	/** The finder. */
	@Inject
	private ClassifiBasicWorkFinder finder;

	/** The save. */
	@Inject
	private ClassifiBWSaveCommandHandler save;

	/** The remove. */
	@Inject
	private ClassifiBWRemoveCommandHandler remove;
	
	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;
	
	/** The internationalization. */
	@Inject
	private IInternationalization internationalization;

	/**
	 * Find all.
	 *
	 * @param classifyCode the classify code
	 * @return the classifi basic work find dto
	 */
	@POST
	@Path("find/{classifyCode}")
	public ClassifiBasicWorkFindDto findAll(@PathParam("classifyCode") String classifyCode) {
		return this.finder.findAll(classifyCode);
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
	public List<String> save(ClassifiBWSaveCommand command) {
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
	public void delete(ClassifiBWRemoveCommand command) {
		this.remove.handle(command);
	}
}
