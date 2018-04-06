/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveWkpStatWorkTimeSetCommandHandler extends CommandHandler<SaveWkpStatWorkTimeSetCommand> {

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
	private WkpTransLaborTimeRepository wkpSpeDeforLaborTimeRepository;

	/* 
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpStatWorkTimeSetCommand> context) {

		SaveWkpStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String wkpId = command.getWorkplaceId();
		String companyId = AppContexts.user().companyId();

		WkpNormalSetting wkpNormalSetting = command.getNormalSetting().toWkpDomain(year, wkpId);
		WkpFlexSetting wkpFlexSetting = command.getFlexSetting().toWkpDomain(year, wkpId);
		WkpDeforLaborSetting wkpDeforLaborSetting = command.getDeforLaborSetting().toWkpDomain(year, wkpId);
		WkpRegularLaborTime wkpRegularLaborTime = command.getRegularLaborTime().toWkpRegularLaborTimeDomain(wkpId);
		WkpTransLaborTime wkpTransLaborTime = command.getTransLaborTime().toWkpTransTimeDomain(wkpId);
		
		Optional<WkpNormalSetting> optWkpNormalSet = this.wkpNormalSettingRepository.find(companyId, wkpId, year);
		// Check info WkpNormalSetting if exist -> update into db / not exist -> insert into DB
		if(optWkpNormalSet.isPresent()){
			this.wkpNormalSettingRepository.update(wkpNormalSetting);
		} else {
			this.wkpNormalSettingRepository.add(wkpNormalSetting);
		}
		
		Optional<WkpFlexSetting> optWkpFlexSet = this.wkpFlexSettingRepository.find(companyId, wkpId, year);
		// Check info WkpFlexSetting if exist -> update into db / not exist -> insert into DB
		if(optWkpFlexSet.isPresent()) {
			this.wkpFlexSettingRepository.update(wkpFlexSetting);
		} else {
			this.wkpFlexSettingRepository.add(wkpFlexSetting);
		}
		
		Optional<WkpDeforLaborSetting> optWkpDeforSet = this.wkpDeforLaborSettingRepository.find(companyId, wkpId, year);
		// Check info WkpDeforLaborSetting if exist -> update into db / not exist -> insert into DB
		if(optWkpDeforSet.isPresent()) {
			this.wkpDeforLaborSettingRepository.update(wkpDeforLaborSetting);
		} else {
			this.wkpDeforLaborSettingRepository.add(wkpDeforLaborSetting);
		}
		
		Optional<WkpRegularLaborTime> optWkpRegularSet = this.wkpRegularWorkTimeRepository.find(companyId, wkpId);
		// Check info WkpRegularLaborTime if exist -> update into db / not exist -> insert into DB
		if(optWkpRegularSet.isPresent()){
			this.wkpRegularWorkTimeRepository.update(wkpRegularLaborTime);
		} else {
			this.wkpRegularWorkTimeRepository.add(wkpRegularLaborTime);
		}
		
		Optional<WkpTransLaborTime> optWkpTransSet = this.wkpSpeDeforLaborTimeRepository.find(companyId, wkpId);
		// Check info WkpTransLaborTime if exist -> update into db / not exist -> insert into DB
		if(optWkpTransSet.isPresent()) {
			this.wkpSpeDeforLaborTimeRepository.update(wkpTransLaborTime);
		} else {
			this.wkpSpeDeforLaborTimeRepository.add(wkpTransLaborTime);
		}

	}

}
