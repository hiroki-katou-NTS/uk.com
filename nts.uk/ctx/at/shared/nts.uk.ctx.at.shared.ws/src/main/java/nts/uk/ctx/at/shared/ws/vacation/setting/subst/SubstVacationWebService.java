/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.vacation.setting.subst;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.error.BusinessException;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.ComSubstVacationSaveCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.ComSubstVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.EmpSubstVacationSaveCommand;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.command.EmpSubstVacationSaveCommandHandler;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.find.dto.EmpSubstVacationDto;
import nts.uk.ctx.at.shared.app.vacation.setting.subst.find.dto.SubstVacationSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.VacationExpiration;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class SubstVacationWebService.
 */
@Path("at/proto/substvacation/")
@Produces(MediaType.APPLICATION_JSON)
public class SubstVacationWebService extends WebService {

	/** The com subst vacation save command handler. */
	@Inject
	private ComSubstVacationSaveCommandHandler comSubstVacationSaveCommandHandler;

	/** The emp subst vacation save command handler. */
	@Inject
	private EmpSubstVacationSaveCommandHandler empSubstVacationSaveCommandHandler;

	/** The com sv repository. */
	@Inject
	private ComSubstVacationRepository comSvRepository;

	/** The emp sv repository. */
	@Inject
	private EmpSubstVacationRepository empSvRepository;

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

		Optional<ComSubstVacation> optComSubVacation = this.comSvRepository.findById(companyId);

		if (!optComSubVacation.isPresent()) {
			throw new BusinessException("");
		}

		SubstVacationSettingDto dto = new SubstVacationSettingDto();

		optComSubVacation.get().saveToMemento(dto);

		return dto;
	}

	/**
	 * Find setting by contract type code.
	 *
	 * @param contractTypeCode
	 *            the contract type code
	 * @return the emp subst vacation dto
	 */
	@POST
	@Path("emp/find/{typeCode}")
	public EmpSubstVacationDto findSettingByContractTypeCode(
			@PathParam("typeCode") String contractTypeCode) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Find setting
		Optional<EmpSubstVacation> optEmpSubVacation = this.empSvRepository.findById(companyId,
				contractTypeCode);

		// Check exist
		if (!optEmpSubVacation.isPresent()) {
			// TODO: find msg id
			throw new BusinessException("");
		}

		EmpSubstVacationDto dto = new EmpSubstVacationDto();

		optEmpSubVacation.get().saveToMemento(dto);

		return dto;
	}

	/**
	 * Gets the specification date.
	 *
	 * @return the specification date
	 */
	@POST
	@Path("find/vacationexpiration")
	public List<EnumConstant> getSpecificationDate() {
		return EnumAdaptor.convertToValueNameList(VacationExpiration.class);
	}

}
