/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.compensatoryleave;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.DeleteEmploymentCompensatoryCommandHandler;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.SaveCompensatoryLeaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.SaveCompensatoryLeaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.SaveEmploymentCompensatoryCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.SaveEmploymentCompensatoryCommandHandler;
import nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.CompensatoryLeaveEmploymentFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.CompensatoryLeaveFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto.CompensatoryLeaveComSettingDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.compensatoryleave.dto.CompensatoryLeaveEmSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.DeadlCheckMonth;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;

/**
 * The Class AnnualPaidLeaveWs.
 */
@Path("ctx/at/shared/vacation/setting/compensatoryleave/")
@Produces("application/json")
public class CompensatoryLeaveWs extends WebService {

	/** The annual finder. */
	@Inject
	private CompensatoryLeaveFinder compensatoryLeaveFinder;

	/** The save compensatory leave command handler. */
	@Inject
	private SaveCompensatoryLeaveCommandHandler saveCompensatoryLeaveCommandHandler;
	
	/** The save employment compensatory command handler. */
	@Inject
	private SaveEmploymentCompensatoryCommandHandler saveEmploymentCompensatoryCommandHandler;
	
	/** The compensatory leave employment finder. */
	@Inject
	private CompensatoryLeaveEmploymentFinder compensatoryLeaveEmploymentFinder;
	
	/** The delete employment compensatory command handler. */
	@Inject
	private DeleteEmploymentCompensatoryCommandHandler deleteEmploymentCompensatoryCommandHandler;
	
	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("save")
	public void save(SaveCompensatoryLeaveCommand command) {
		this.saveCompensatoryLeaveCommandHandler.handle(command);
	}
	
	/**
	 * Find by company id.
	 *
	 * @return the annual paid leave setting
	 */
	@POST
	@Path("find")
	public CompensatoryLeaveComSettingDto findByCompanyId() {
		return compensatoryLeaveFinder.findByCompanyId();
	}

	/**
	 * Gets the vacation expiration enum.
	 *
	 * @return the vacation expiration enum
	 */
	@POST
	@Path("enum/managedistinct")
	public List<EnumConstant> getManageDistinctEnum() {
		return EnumAdaptor.convertToValueNameList(ManageDistinct.class);
	}
	
	/**
	 * Gets the apply permission enum.
	 *
	 * @return the apply permission enum
	 */
	@POST
	@Path("enum/applypermission")
	public List<EnumConstant> getApplyPermissionEnum() {
		return EnumAdaptor.convertToValueNameList(ApplyPermission.class);
	}
	
	/**
	 * Gets the expiration time enum.
	 *
	 * @return the expiration time enum
	 */
	@POST
	@Path("enum/expirationtime")
	public List<EnumConstant> getExpirationTimeEnum() {
		return EnumAdaptor.convertToValueNameList(ExpirationTime.class);
	}
	
	/**
	 * Gets the deadl check month.
	 *
	 * @return the deadl check month
	 */
	@POST
	@Path("enum/deadlcheckmonth")
	public List<EnumConstant> getDeadlCheckMonth() {
		return EnumAdaptor.convertToValueNameList(DeadlCheckMonth.class);
	}

	/**
	 * Gets the time vacation digestive unit enum.
	 *
	 * @return the time vacation digestive unit enum
	 */
	@POST
	@Path("enum/timevacationdigestiveunit")
	public List<EnumConstant> getTimeVacationDigestiveUnitEnum() {
		return EnumAdaptor.convertToValueNameList(TimeDigestiveUnit.class);
	}

	/**
	 * Gets the compensatory occurrence division enum.
	 *
	 * @return the compensatory occurrence division enum
	 */
	@POST
	@Path("enum/compensatoryoccurrencedivision")
	public List<EnumConstant> getCompensatoryOccurrenceDivisionEnum() {
		return EnumAdaptor.convertToValueNameList(CompensatoryOccurrenceDivision.class);
	}

	/**
	 * Gets the transfer setting division enum.
	 *
	 * @return the transfer setting division enum
	 */
	@POST
	@Path("enum/transfersettingdivision")
	public List<EnumConstant> getTransferSettingDivisionEnum() {
		return EnumAdaptor.convertToValueNameList(SubHolTransferSetAtr.class);
	}
	
	/**
	 * Gets the all employment.
	 *
	 * @return the all employment
	 */
	@POST
	@Path("employment/findall")
	public List<String> getAllEmployment() {
		return compensatoryLeaveEmploymentFinder.findAllEmployment();
	}
	
	/**
	 * Save employment setting.
	 *
	 * @param command the command
	 * @return the list
	 */
	@POST
	@Path("employment/save")
	public void saveEmploymentSetting(SaveEmploymentCompensatoryCommand command) {
		saveEmploymentCompensatoryCommandHandler.handle(command);
	}
	
	/**
	 * Gets the employment setting.
	 *
	 * @param employmentCode the employment code
	 * @return the employment setting
	 */
	@POST
	@Path("employment/findsetting/{employmentCode}")
	public CompensatoryLeaveEmSettingDto getEmploymentSetting(@PathParam("employmentCode") String employmentCode) {
		return compensatoryLeaveEmploymentFinder.findByEmploymentCode(employmentCode);
	}
	
	/**
	 * Delete.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("employment/delete")
	public void deleteEmploymentSetting(SaveEmploymentCompensatoryCommand command) {
		this.deleteEmploymentCompensatoryCommandHandler.handle(command);
	}
}
