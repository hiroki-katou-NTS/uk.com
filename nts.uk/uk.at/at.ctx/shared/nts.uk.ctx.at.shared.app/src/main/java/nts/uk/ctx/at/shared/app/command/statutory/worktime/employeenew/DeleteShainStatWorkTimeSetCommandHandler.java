/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteShainStatWorkTimeSetCommandHandler.
 */
@Stateless
public class DeleteShainStatWorkTimeSetCommandHandler extends CommandHandler<DeleteShainStatWorkTimeSetCommand> {

	/** The shain normal setting repository. */
	@Inject
	private ShainNormalSettingRepository shainNormalSettingRepository;

	/** The shain flex setting repository. */
	@Inject
	private ShainFlexSettingRepository shainFlexSettingRepository;

	/** The shain defor labor setting repository. */
	@Inject
	private ShainDeforLaborSettingRepository shainDeforLaborSettingRepository;

	/** The shain regular work time repository. */
	@Inject
	private ShainRegularWorkTimeRepository shainRegularWorkTimeRepository;

	/** The shain spe defor labor time repository. */
	@Inject
	private ShainTransLaborTimeRepository shainSpeDeforLaborTimeRepository;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteShainStatWorkTimeSetCommand> context) {
		DeleteShainStatWorkTimeSetCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		String employeeId = command.getEmployeeId();

		this.shainNormalSettingRepository.find(companyId, employeeId, year)
				.ifPresent((setting) -> this.shainNormalSettingRepository.delete(companyId, employeeId, year));
		this.shainFlexSettingRepository.find(companyId, employeeId, year)
				.ifPresent((setting) -> this.shainFlexSettingRepository.delete(companyId, employeeId, year));
		this.shainDeforLaborSettingRepository.find(companyId, employeeId, year)
				.ifPresent((setting) -> this.shainDeforLaborSettingRepository.delete(companyId, employeeId, year));

		// set isOverOneYear == true
		command.setOverOneYear(true);

		// if isOverOneYearRegister == false, remove remain domains
		if (!this.isOverOneYearRegister(companyId, employeeId)) {

			this.shainRegularWorkTimeRepository.find(companyId, employeeId)
					.ifPresent((setting) -> this.shainRegularWorkTimeRepository.delete(companyId, employeeId));
			this.shainSpeDeforLaborTimeRepository.find(companyId, employeeId)
					.ifPresent((setting) -> this.shainSpeDeforLaborTimeRepository.delete(companyId, employeeId));

			// set isOverOneYear == false
			command.setOverOneYear(false);
		}

	}

	/**
	 * Checks if is over one year register.
	 *
	 * @param cid
	 *            the cid
	 * @param employeeId
	 *            the employee id
	 * @return true, if is over one year register
	 */
	public boolean isOverOneYearRegister(String cid, String employeeId) {

		// find list sha normal setting register
		List<ShainNormalSetting> listShainNormalSetting = this.shainNormalSettingRepository.findList(cid, employeeId);

		// check list sha normal setting > 0
		if (!listShainNormalSetting.isEmpty() && listShainNormalSetting.size() > 0) {
			return true;
		}
		return false;

	}

}
