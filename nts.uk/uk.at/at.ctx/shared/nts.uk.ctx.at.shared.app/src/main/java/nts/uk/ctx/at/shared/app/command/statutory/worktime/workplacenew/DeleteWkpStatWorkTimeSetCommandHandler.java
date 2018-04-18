/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteWkpStatWorkTimeSetCommandHandler.
 */
@Stateless
public class DeleteWkpStatWorkTimeSetCommandHandler extends CommandHandler<DeleteWkpStatWorkTimeSetCommand> {

	/** The wkp normal setting repository. */
	@Inject
	private WkpNormalSettingRepository wkpNormalSettingRepository;

	/** The wkp flex setting repository. */
	@Inject
	private WkpFlexSettingRepository wkpFlexSettingRepository;

	/** The wkp defor labor setting repository. */
	@Inject
	private WkpDeforLaborSettingRepository wkpDeforLaborSettingRepository;

	/** The wkp regular work time repository. */
	@Inject
	private WkpRegularLaborTimeRepository wkpRegularWorkTimeRepository;

	/** The wkp spe defor labor time repository. */
	@Inject
	private WkpTransLaborTimeRepository wkpTransLaborTimeRepository;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteWkpStatWorkTimeSetCommand> context) {
		DeleteWkpStatWorkTimeSetCommand command = context.getCommand();

		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		String wkpId = command.getWorkplaceId();

		// remove domains belong to year
		this.wkpNormalSettingRepository.find(companyId, wkpId, year)
				.ifPresent((setting) -> this.wkpNormalSettingRepository.remove(companyId, wkpId, year));
		this.wkpFlexSettingRepository.find(companyId, wkpId, year)
				.ifPresent((setting) -> this.wkpFlexSettingRepository.remove(companyId, wkpId, year));
		this.wkpDeforLaborSettingRepository.find(companyId, wkpId, year)
				.ifPresent((setting) -> this.wkpDeforLaborSettingRepository.remove(companyId, wkpId, year));

		// set isOverOneYear == true
		command.setOverOneYear(true);
		
		// if isOverOneYearRegister == false, remove remain domains
		if (!this.isOverOneYearRegister(companyId, wkpId)) {
			this.wkpRegularWorkTimeRepository.find(companyId, wkpId)
					.ifPresent((setting) -> this.wkpRegularWorkTimeRepository.remove(companyId, wkpId));
			this.wkpTransLaborTimeRepository.find(companyId, wkpId)
					.ifPresent((setting) -> this.wkpTransLaborTimeRepository.remove(companyId, wkpId));

			// set isOverOneYear == false
			command.setOverOneYear(false);
		}		
	}

	/**
	 * Checks if is over one year register.
	 *
	 * @param cid the cid
	 * @param wkpId the wkp id
	 * @return true, if is over one year register
	 */
	public boolean isOverOneYearRegister(String cid, String wkpId) {

		// find list wkp normal setting register
		List<WkpNormalSetting> listWkpNormalSetting = this.wkpNormalSettingRepository.findList(cid, wkpId);

		// check list wkp normal setting > 0
		if (!listWkpNormalSetting.isEmpty() && listWkpNormalSetting.size() > 0) {
			return true;
		}
		return false;

	}

}
