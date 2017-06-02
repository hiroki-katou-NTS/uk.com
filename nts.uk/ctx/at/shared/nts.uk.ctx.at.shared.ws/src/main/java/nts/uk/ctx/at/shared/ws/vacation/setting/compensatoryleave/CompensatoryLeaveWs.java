/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.compensatoryleave;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.SaveCompensatoryLeaveCommand;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command.SaveCompensatoryLeaveCommandHandler;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.find.CompensatoryLeaveFinder;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.find.dto.CompensatoryLeaveComSettingDto;

/**
 * The Class AnnualPaidLeaveWs.
 */
@Path("ctx/at/shared/vacation/setting/compensatoryleave/")
@Produces("application/json")
public class CompensatoryLeaveWs extends WebService {

	/** The annual finder. */
	@Inject
	private CompensatoryLeaveFinder compensatoryLeaveFinder;

	@Inject
	private SaveCompensatoryLeaveCommandHandler saveCompensatoryLeaveCommandHandler;

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
	 * Gets the time vacation digestive unit enum.
	 *
	 * @return the time vacation digestive unit enum
	 */
	@POST
	@Path("enum/timevacationdigestiveunit")
	public List<EnumConstant> getTimeVacationDigestiveUnitEnum() {
		return EnumAdaptor.convertToValueNameList(TimeVacationDigestiveUnit.class);
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
		return EnumAdaptor.convertToValueNameList(TransferSettingDivision.class);
	}
	
	/**
	 * Gets the all employment.
	 *
	 * @return the all employment
	 */
	@POST
	@Path("employment/findall")
	public List<EnumConstant> getAllEmployment() {
		//TODO mock data list employment
		return null;
	}
	
	/**
	 * Save employment setting.
	 *
	 * @return the list
	 */
	@POST
	@Path("employment/save")
	public List<EnumConstant> saveEmploymentSetting() {
		return null;
	}
	
	/**
	 * Gets the employment setting.
	 *
	 * @return the employment setting
	 */
	@POST
	@Path("employment/findsetting")
	public List<EnumConstant> getEmploymentSetting() {
		return null;
	}
}
