/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveShainStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveEmpStatWorkTimeSetCommandHandler extends CommandHandler<SaveEmpStatWorkTimeSetCommand> {

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
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmpStatWorkTimeSetCommand> context) {

		SaveEmpStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String emplCode = command.getEmploymentCode();
		String companyId = AppContexts.user().companyId();

		EmpNormalSetting empNormalSetting = command.getNormalSetting().toEmpDomain(year, emplCode);
		EmpFlexSetting empFlexSetting = command.getFlexSetting().toEmpDomain(year, emplCode);
		EmpDeforLaborSetting empDeforLaborSetting = command.getDeforLaborSetting().toEmpDomain(year, emplCode);
		EmpRegularLaborTime empRegularLaborTime = command.getRegularLaborTime().toEmpRegularTimeDomain(emplCode);
		EmpTransLaborTime empTransLaborTime = command.getTransLaborTime().toEmpTransTimeDomain(emplCode);
		
		Optional<EmpNormalSetting> optEmpNormalSet = this.empNormalSettingRepository.find(companyId, emplCode, year);
		// Check info EmpNormalSetting if exist -> update into db / not exist -> insert into DB
		if(optEmpNormalSet.isPresent()){
			this.empNormalSettingRepository.update(empNormalSetting);
		} else {
			this.empNormalSettingRepository.add(empNormalSetting);
		}
		
		Optional<EmpFlexSetting> optEmpFlexSet = this.empFlexSettingRepository.find(companyId, emplCode, year);
		if(optEmpFlexSet.isPresent()) {
			this.empFlexSettingRepository.update(empFlexSetting);
		} else {
			this.empFlexSettingRepository.add(empFlexSetting);
		}
		
		Optional<EmpDeforLaborSetting> optEmpDeforSet = this.empDeforLaborSettingRepository.find(companyId, emplCode, year);
		// Check info EmpDeforLaborSetting if exist -> update into db / not exist -> insert into DB
		if(optEmpDeforSet.isPresent()) {
			this.empDeforLaborSettingRepository.update(empDeforLaborSetting);
		} else {
			this.empDeforLaborSettingRepository.add(empDeforLaborSetting);
		}
		
		Optional<EmpRegularLaborTime> optEmpRegularSet = this.empRegularWorkTimeRepository.findById(companyId, emplCode);
		// Check info EmpRegularLaborTime if exist -> update into db / not exist -> insert into DB
		if(optEmpRegularSet.isPresent()){
			this.empRegularWorkTimeRepository.update(empRegularLaborTime);
		} else {
			this.empRegularWorkTimeRepository.add(empRegularLaborTime);
		}
		
		Optional<EmpTransLaborTime> optEmpTransSet = this.empTransWorkTimeRepository.find(companyId, emplCode);
		// Check info EmpTransLaborTime if exist -> update into db / not exist -> insert into DB
		if(optEmpTransSet.isPresent()) {
			this.empTransWorkTimeRepository.update(empTransLaborTime);
		} else {
			this.empTransWorkTimeRepository.add(empTransLaborTime);
		}

	}

}

