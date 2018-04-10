/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteComDeformationLaborSettingCommandHandler.
 */
@Stateless
public class DeleteComStatWorkTimeSetCommandHandler
		extends CommandHandler<DeleteComStatWorkTimeSetCommand> {

	@Inject
	private ComNormalSettingRepository comNormalSettingRepository;
	
	@Inject
	private ComFlexSettingRepository comFlexSettingRepository;
	
	@Inject
	private ComDeforLaborSettingRepository comDeforLaborSettingRepository;
	
	@Inject
	private ComRegularLaborTimeRepository comRegularLaborTimeRepository;
	
	@Inject
	private ComTransLaborTimeRepository comTransLaborTimeRepository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteComStatWorkTimeSetCommand> context) {
		DeleteComStatWorkTimeSetCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		int year = command.getYear();
		
		// remove ComNormalSetting, ComFlexSetting, ComDeforLaborSetting, ComRegularLaborTime, ComTransLaborTime with companyID & year if present
		this.comNormalSettingRepository.find(companyId, year).ifPresent((setting) -> this.comNormalSettingRepository.remove(companyId, year));
		this.comFlexSettingRepository.find(companyId, year).ifPresent((setting) -> this.comFlexSettingRepository.remove(companyId, year));
		this.comDeforLaborSettingRepository.find(companyId, year).ifPresent((setting) -> this.comDeforLaborSettingRepository.remove(companyId, year));
		this.comRegularLaborTimeRepository.find(companyId).ifPresent((setting) -> this.comRegularLaborTimeRepository.remove(companyId));
		this.comTransLaborTimeRepository.find(companyId).ifPresent((setting) -> this.comTransLaborTimeRepository.remove(companyId));
	}

}
