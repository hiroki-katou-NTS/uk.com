/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.sixtyhour;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours.Com60HourVacationSaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours.Com60HourVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours.Emp60HourVacationSaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours.Emp60HourVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.SixtyHourVacationFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.Emp60HourVacationDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingCheckDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.sixtyhours.dto.SixtyHourVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class SixtyHourVacationWebService.
 */
@Path("ctx/at/shared/vacation/setting/sixtyhourvacation/")
@Produces(MediaType.APPLICATION_JSON)
public class SixtyHourVacationWebService {
	
	/** The com 60 H vacation save command handler. */
	@Inject
	private Com60HourVacationSaveCommandHandler com60HVacationSaveCommandHandler;
	
	/** The emp 60 H vacation save command handler. */
	@Inject
	private Emp60HourVacationSaveCommandHandler emp60HVacationSaveCommandHandler;
	
	/** The sixty hour vacation finder. */
	@Inject
	private SixtyHourVacationFinder sixtyHourVacationFinder;
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("com/save")
	public void save(Com60HourVacationSaveCommand command) {
		this.com60HVacationSaveCommandHandler.handle(command);
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("emp/save")
	public void save(Emp60HourVacationSaveCommand command) {
		this.emp60HVacationSaveCommandHandler.handle(command);
	}
	
	/**
	 * Find com setting.
	 *
	 * @return the sixty hour vacation setting dto
	 */
	@POST
	@Path("com/find")
	public SixtyHourVacationSettingDto findComSetting() {
		return this.sixtyHourVacationFinder.findComSetting();
	}
	
	/**
	 * Find emp setting.
	 *
	 * @param contractTypeCode the contract type code
	 * @return the emp 60 hour vacation dto
	 */
	@POST
	@Path("emp/find/{typeCode}")
	public Emp60HourVacationDto findEmpSetting(@PathParam("typeCode") String contractTypeCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Return
		return this.sixtyHourVacationFinder.findEmpSetting(companyId, contractTypeCode);
	}
	
	/**
	 * Gets the all employment.
	 *
	 * @return the all employment
	 */
	@POST
	@Path("emp/findall")
	public List<String> getAllEmployment() {
		return this.sixtyHourVacationFinder.findAllEmployment();
	}
	
	/**
	 * Gets the manage distinct.
	 *
	 * @return the manage distinct
	 */
	@POST
	@Path("enum/managedistinct")
	public List<EnumConstant> getManageDistinct() {
		return EnumAdaptor.convertToValueNameList(ManageDistinct.class);
	}
	
	/**
	 * Gets the sixty hour extra.
	 *
	 * @return the sixty hour extra
	 */
	@POST
	@Path("enum/sixtyhourextra")
	public List<EnumConstant> getSixtyHourExtra() {
		return EnumAdaptor.convertToValueNameList(SixtyHourExtra.class);
	}
	
	/**
	 * Gets the time digestive unit.
	 *
	 * @return the time digestive unit
	 */
	@POST
	@Path("enum/timedigestiveunit")
	public List<EnumConstant> getTimeDigestiveUnit() {
		return EnumAdaptor.convertToValueNameList(TimeDigestiveUnit.class);
	}
	
	/**
	 * Check manange setting.
	 *
	 * @return the sixty hour vacation setting check dto
	 */
	@POST
	@Path("com/check/manage")
	public SixtyHourVacationSettingCheckDto checkManangeSetting() {
		return this.sixtyHourVacationFinder.checkManangeSetting();
	}
}
