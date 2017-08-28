/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.basicworkregister;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.error.BusinessException;
import nts.arc.i18n.custom.IInternationalization;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.CompanyBWSaveCommand;
import nts.uk.ctx.at.schedule.app.command.shift.basicworkregister.CompanyBWSaveCommandHandler;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.CompanyBasicWorkFinder;
import nts.uk.ctx.at.schedule.app.find.shift.basicworkregister.dto.CompanyBasicWorkFindDto;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;

/**
 * The Class CompanyBasicWorkWebService.
 */
@Path("ctx/at/schedule/shift/basicworkregister/companybasicwork/")
@Produces("application/json")
public class CompanyBasicWorkWebService extends WebService {

	/** The finder. */
	@Inject
	private CompanyBasicWorkFinder finder;

	/** The save. */
	@Inject
	private CompanyBWSaveCommandHandler save;

	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;

	/** The internationalization. */
	@Inject
	private IInternationalization internationalization;

	/**
	 * Find all.
	 *
	 * @param workdayDivision
	 *            the workday division
	 * @return the company basic work find dto
	 */
	@POST
	@Path("find")
	public CompanyBasicWorkFindDto findAll() {
		return this.finder.findAll();
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(CompanyBWSaveCommand command) {
		// Check Worktype:
		BusinessException businessException = new BusinessException("Msg_178", "KSM006_6");
		command.getBasicWorkSetting().stream().forEach(item -> {
			WorkStyle workStyle = basicScheduleService.checkWorkDay(item.getWorktypeCode().v());
			if (WorkStyle.ONE_DAY_REST.equals(workStyle)) {
				switch (item.getWorkdayDivision()) {
				case WORKINGDAYS:
					businessException.setSuppliment("KSM006_6",
							(new BusinessException("Msg_178", "KSM006_6")).getMessage());
				case NON_WORKINGDAY_INLAW:
					businessException.setSuppliment("KSM006_7",
							(new BusinessException("Msg_179", "KSM006_7")).getMessage());
				case NON_WORKINGDAY_EXTRALEGAL:
					businessException.setSuppliment("KSM006_8",
							(new BusinessException("Msg_179", "KSM006_8")).getMessage());
				}
			}
		});

		if (!businessException.getSupplements().isEmpty()) {
			throw businessException;
		}

		// check pair WorkTypeCode - WorkTimeCode require
		command.getBasicWorkSetting().stream().forEach(item -> {
			this.basicScheduleService.checkPairWorkTypeWorkTime(item.getWorktypeCode().v(),
					item.getWorkingCode().v());
		});

		this.save.handle(command);
	}
}
