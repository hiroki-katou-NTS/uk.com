/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveShainStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveShainStatWorkTimeSetCommandHandler extends CommandHandler<SaveShainStatWorkTimeSetCommand> {

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
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveShainStatWorkTimeSetCommand> context) {

		SaveShainStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String employeeId = command.getEmployeeId();
		String companyId = AppContexts.user().companyId();

		ShainNormalSetting shainNormalSetting = command.getNormalSetting().toShainDomain(year, employeeId);
		ShainFlexSetting shainFlexSetting = command.getFlexSetting().toShainDomain(year, employeeId);
		ShainDeforLaborSetting shainDeforLaborSetting = command.getDeforLaborSetting().toShainDomain(year, employeeId);
		ShainRegularLaborTime shainRegularLaborTime = command.getRegularLaborTime().toShainRegularTimeDomain(employeeId);
		ShainTransLaborTime shainTransLaborTime = command.getTransLaborTime().toShainSpeTimeDomain(employeeId);
		
		Optional<ShainNormalSetting> optComNormalSet = this.shainNormalSettingRepository.find(companyId, employeeId, year);
		// Check info ShainNormalSetting if exist -> update into db / not exist -> insert into DB
		if(optComNormalSet.isPresent()){
			this.shainNormalSettingRepository.update(shainNormalSetting);
		} else {
			this.shainNormalSettingRepository.add(shainNormalSetting);
		}
		
		Optional<ShainFlexSetting> optComFlexSet = this.shainFlexSettingRepository.find(companyId, employeeId, year);
		// Check info ShainFlexSetting if exist -> update into db / not exist -> insert into DB
		if(optComFlexSet.isPresent()) {
			this.shainFlexSettingRepository.update(shainFlexSetting);
		} else {
			this.shainFlexSettingRepository.add(shainFlexSetting);
		}
		
		Optional<ShainDeforLaborSetting> optComDeforSet = this.shainDeforLaborSettingRepository.find(companyId, employeeId, year);
		// Check info ShainDeforLaborSetting if exist -> update into db / not exist -> insert into DB
		if(optComDeforSet.isPresent()) {
			this.shainDeforLaborSettingRepository.update(shainDeforLaborSetting);
		} else {
			this.shainDeforLaborSettingRepository.add(shainDeforLaborSetting);
		}
		
		Optional<ShainRegularLaborTime> optComRegularSet = this.shainRegularWorkTimeRepository.find(companyId, employeeId);
		// Check info ShainRegularLaborTime if exist -> update into db / not exist -> insert into DB
		if(optComRegularSet.isPresent()){
			this.shainRegularWorkTimeRepository.update(shainRegularLaborTime);
		} else {
			this.shainRegularWorkTimeRepository.add(shainRegularLaborTime);
		}
		
		Optional<ShainTransLaborTime> optComTransSet = this.shainSpeDeforLaborTimeRepository.find(companyId, employeeId);
		// Check info ShainTransLaborTime if exist -> update into db / not exist -> insert into DB
		if(optComTransSet.isPresent()) {
			this.shainSpeDeforLaborTimeRepository.update(shainTransLaborTime);
		} else {
			this.shainSpeDeforLaborTimeRepository.add(shainTransLaborTime);
		}

	}

}
