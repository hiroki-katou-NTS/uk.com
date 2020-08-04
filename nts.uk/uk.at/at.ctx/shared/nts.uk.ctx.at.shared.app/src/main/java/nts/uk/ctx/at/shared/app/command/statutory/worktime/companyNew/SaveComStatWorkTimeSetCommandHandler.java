/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveComStatWorkTimeSetCommandHandler.
 */
@Stateless
@Transactional
public class SaveComStatWorkTimeSetCommandHandler extends CommandHandler<SaveComStatWorkTimeSetCommand> {

	/** The com normal setting repository. */
	@Inject
	private ComNormalSettingRepository comNormalSettingRepository;

	/** The com flex setting repository. */
	@Inject
	private ComFlexSettingRepository comFlexSettingRepository;

	/** The com defor labor setting repository. */
	@Inject
	private ComDeforLaborSettingRepository comDeforLaborSettingRepository;

	/** The com regular labor time repository. */
	@Inject
	private ComRegularLaborTimeRepository comRegularLaborTimeRepository;

	/** The com trans labor time repository. */
	@Inject
	private ComTransLaborTimeRepository comTransLaborTimeRepository;

	/*
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComStatWorkTimeSetCommand> context) {

		SaveComStatWorkTimeSetCommand command = context.getCommand();
		int year = command.getYear();
		String companyId = AppContexts.user().companyId();

		ComNormalSetting comNormalSetting = command.getNormalSetting().toDomain(year);
		ComFlexSetting comFlexSetting = command.getFlexSetting().toDomain(year);
		ComDeforLaborSetting comDeforLaborSetting = command.getDeforLaborSetting().toDomain(year);
		ComRegularLaborTime comRegularLaborTime = command.getRegularLaborTime().toComRegularLaborTimeDomain();
		ComTransLaborTime comTransLaborTime = command.getTransLaborTime().toComTransLaborTimeDomain();

		Optional<ComNormalSetting> optComNormalSet = this.comNormalSettingRepository.find(companyId, year);
		// Check info ComNormalSetting if exist -> update into db / not exist -> insert into DB
		if (optComNormalSet.isPresent()) {
			this.comNormalSettingRepository.update(comNormalSetting);
		} else {
			this.comNormalSettingRepository.create(comNormalSetting);
		}

		Optional<ComFlexSetting> optComFlexSet = this.comFlexSettingRepository.find(companyId, year);
		// Check info ComFlexSetting if exist -> update into db / not exist -> insert into DB
		if (optComFlexSet.isPresent()) {
			this.comFlexSettingRepository.update(comFlexSetting);
		} else {
			this.comFlexSettingRepository.create(comFlexSetting);
		}

		Optional<ComDeforLaborSetting> optComDeforSet = this.comDeforLaborSettingRepository.find(companyId, year);
		//	Check info ComDeforLaborSetting if exist -> update into db / not exist -> insert into DB
		if (optComDeforSet.isPresent()) {
			this.comDeforLaborSettingRepository.update(comDeforLaborSetting);
		} else {
			this.comDeforLaborSettingRepository.insert(comDeforLaborSetting);
		}

		Optional<ComRegularLaborTime> optComRegularSet = this.comRegularLaborTimeRepository.find(companyId);
		// Check info ComRegularLaborTime if exist -> update into db / not exist -> insert into DB
		if (optComRegularSet.isPresent()) {
			this.comRegularLaborTimeRepository.update(comRegularLaborTime);
		} else {
			this.comRegularLaborTimeRepository.create(comRegularLaborTime);
		}

		Optional<ComTransLaborTime> optComTransSet = this.comTransLaborTimeRepository.find(companyId);
		// Check info ComTransLaborTime if exist -> update into db / not exist -> insert into DB
		if (optComTransSet.isPresent()) {
			this.comTransLaborTimeRepository.update(comTransLaborTime);
		} else {
			this.comTransLaborTimeRepository.create(comTransLaborTime);
		}

	}

}
