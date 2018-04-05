/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteShainStatWorkTimeSetCommandHandler.
 */
@Stateless
public class DeleteEmpStatWorkTimeSetCommandHandler extends CommandHandler<DeleteEmpStatWorkTimeSetCommand> {

	/** The emp normal setting repository. */
	@Inject
	private EmpNormalSettingRepository empNormalSettingRepository;

	/** The emp flex setting repository. */
	@Inject
	private EmpFlexSettingRepository empFlexSettingRepository;

	/** The emp defor labor setting repository. */
	@Inject
	private EmpDeforLaborSettingRepository empDeforLaborSettingRepository;

	/** The emp regular work time repository. */
	@Inject
	private EmpRegularWorkTimeRepository empRegularWorkTimeRepository;

	/** The emp spe defor labor time repository. */
	@Inject
	private EmpTransWorkTimeRepository empTransWorkTimeRepository;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmpStatWorkTimeSetCommand> context) {
		
		DeleteEmpStatWorkTimeSetCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		String emplCode = command.getEmploymentCode();

		// remove domains belong to year
		this.empNormalSettingRepository.find(companyId, emplCode, year)
				.ifPresent((setting -> this.empNormalSettingRepository.delete(companyId, emplCode, year)));
		this.empFlexSettingRepository.find(companyId, emplCode, year)
				.ifPresent((setting -> this.empFlexSettingRepository.delete(companyId, emplCode, year)));
		this.empDeforLaborSettingRepository.find(companyId, emplCode, year)
				.ifPresent((setting -> this.empDeforLaborSettingRepository.delete(companyId, emplCode, year)));

		// set isOverOneYear == true
		command.setOverOneYear(true);
		
		// if isOverOneYearRegister == false, remove remain domains
		if (!this.isOverOneYearRegister(companyId, emplCode)) {
			this.empRegularWorkTimeRepository.findById(companyId, emplCode)
					.ifPresent((setting -> this.empRegularWorkTimeRepository.delete(companyId, emplCode)));
			this.empTransWorkTimeRepository.find(companyId, emplCode)
					.ifPresent((setting -> this.empTransWorkTimeRepository.delete(companyId, emplCode)));

			// set isOverOneYear == false
			command.setOverOneYear(false);
		}

	}

	/**
	 * Checks if is over one year register.
	 *
	 * @param cid
	 *            the cid
	 * @param emplCode
	 *            the empl code
	 * @return true, if is over one year register
	 */
	public boolean isOverOneYearRegister(String cid, String emplCode) {

		// find list emp normal setting register
		List<EmpNormalSetting> listEmpNormalSetting = this.empNormalSettingRepository.findList(cid, emplCode);

		// check list emp normal setting > 0
		if (!listEmpNormalSetting.isEmpty() && listEmpNormalSetting.size() > 0) {
			return true;
		}
		return false;

	}

}
