/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.subst;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.vacation.setting.subst.ComSubstVacationSaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.subst.ComSubstVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.app.command.vacation.setting.subst.EmpSubstVacationSaveCommand;
import nts.uk.ctx.at.shared.app.command.vacation.setting.subst.EmpSubstVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.app.delete.vacation.setting.subst.DeleteSubstVacationUtil;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.SubstVacationFinder;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.EmpSubstVacationDto;
import nts.uk.ctx.at.shared.app.find.vacation.setting.subst.dto.SubstVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ManageDeadline;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class SubstVacationWebService.
 */
@Path("ctx/at/shared/vacation/setting/substvacation/")
@Produces(MediaType.APPLICATION_JSON)
public class SubstVacationWebService extends WebService {

	/** The com subst vacation save command handler. */
	@Inject
	private ComSubstVacationSaveCommandHandler comSubstVacationSaveCommandHandler;

	/** The emp subst vacation save command handler. */
	@Inject
	private EmpSubstVacationSaveCommandHandler empSubstVacationSaveCommandHandler;

	/** The subst vacation finder. */
	@Inject
	private SubstVacationFinder substVacationFinder;
	
	@Inject
	private DeleteSubstVacationUtil deleteSubstVacationUtil;

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("com/save")
	public void save(ComSubstVacationSaveCommand command) {
		this.comSubstVacationSaveCommandHandler.handle(command);
	}

	/**
	 * Save.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("emp/save")
	public void save(EmpSubstVacationSaveCommand command) {
		this.empSubstVacationSaveCommandHandler.handle(command);
	}

	/**
	 * Find com setting.
	 *
	 * @return the subst vacation setting dto
	 */
	@POST
	@Path("com/find")
	public SubstVacationSettingDto findComSetting() {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Return
		return this.substVacationFinder.findComSetting(companyId);
	}

	/**
	 * Find emp setting.
	 *
	 * @param contractTypeCode
	 *            the contract type code
	 * @return the emp subst vacation dto
	 */
	@POST
	@Path("emp/find/{typeCode}")
	public EmpSubstVacationDto findEmpSetting(@PathParam("typeCode") String contractTypeCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Return
		return this.substVacationFinder.findEmpSetting(companyId, contractTypeCode);
	}
	
	/**
	 * Delete.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("emp/delete/{typeCode}")
	public void delete(@PathParam("typeCode") String contractTypeCode) {
		this.deleteSubstVacationUtil.delete(contractTypeCode);
	}
	
	@POST
	@Path("emp/findall")
	public List<String> getAllEmployment() {
		return this.substVacationFinder.findAllEmployment();
	}

	/**
	 * Gets the vacation expiration enum.
	 *
	 * @return the vacation expiration enum
	 */
	@POST
	@Path("enum/vacationexpiration")
	public List<EnumConstant> getVacationExpirationEnum() {
		return EnumAdaptor.convertToValueNameList(ExpirationTime.class);
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
	 * Gets the manage distinct enum.
	 *
	 * @return the manage distinct enum
	 */
	@POST
	@Path("enum/managedistinct")
	public List<EnumConstant> getManageDistinctEnum() {
		return EnumAdaptor.convertToValueNameList(ManageDistinct.class);
	}
	@POST
	@Path("enum/managedeadline")
	public List<EnumConstant> getManagedeadlineEnum() {
		return EnumAdaptor.convertToValueNameList(ManageDeadline.class);
	}
	@POST
	@Path("enum/linkingManagementATR")
	public List<EnumConstant> getLinkManageATREnum() {
		return EnumAdaptor.convertToValueNameList(ManageDistinct.class);
	}
	
	
}
