/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryAcquisitionUseDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryDigestiveTimeUnitDto;
import nts.uk.ctx.at.shared.app.command.vacation.setting.compensatoryleave.dto.CompensatoryOccurrenceSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.TransferSettingDivision;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveCompensatoryLeaveCommandHandler.
 */
@Stateless
public class SaveCompensatoryLeaveCommandHandler extends CommandHandler<SaveCompensatoryLeaveCommand> {

	/** The compens leave com set repository. */
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveCompensatoryLeaveCommand> context) {
		SaveCompensatoryLeaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		boolean compensManage = command.getIsManaged() != ManageDistinct.YES.value;

		CompensatoryLeaveComSetting findClsc = compensLeaveComSetRepository.find(companyId);
		if (findClsc != null) {
			CompensatoryOccurrenceSetting overTime = null;
			CompensatoryOccurrenceSetting workTime = null;
			//get Occurrence from find domain
			if (findClsc.getCompensatoryOccurrenceSetting().get(0)
					.getOccurrenceType().value == CompensatoryOccurrenceDivision.FromOverTime.value) {
				overTime = findClsc.getCompensatoryOccurrenceSetting().get(0);
				workTime = findClsc.getCompensatoryOccurrenceSetting().get(1);
			} else {
				workTime = findClsc.getCompensatoryOccurrenceSetting().get(0);
				overTime = findClsc.getCompensatoryOccurrenceSetting().get(1);
			}
			//DigestiveTimeUnit
			if (compensManage || (command.getCompensatoryDigestiveTimeUnit().getIsManageByTime() != ManageDistinct.YES.value)) {
				CompensatoryDigestiveTimeUnitDto digest = command.getCompensatoryDigestiveTimeUnit();
				digest.setDigestiveUnit(findClsc.getCompensatoryDigestiveTimeUnit().getDigestiveUnit().value);
			}
			
			if (compensManage) {
				//ExpirationTime
				CompensatoryAcquisitionUseDto acqui = command.getCompensatoryAcquisitionUse();
				acqui.setExpirationTime(findClsc.getCompensatoryAcquisitionUse().getExpirationTime().value);
				//PreemptionPermit
				command.getCompensatoryAcquisitionUse()
						.setPreemptionPermit(findClsc.getCompensatoryAcquisitionUse().getPreemptionPermit().value);
				//ManageByTime
				command.getCompensatoryDigestiveTimeUnit()
						.setIsManageByTime(findClsc.getCompensatoryDigestiveTimeUnit().getIsManageByTime().value);
			}

			if (!command.getCompensatoryOccurrenceSetting().isEmpty()) {
				for (CompensatoryOccurrenceSettingDto item : command.getCompensatoryOccurrenceSetting()) {
					// Over time
					if (item.getOccurrenceType() == CompensatoryOccurrenceDivision.FromOverTime.value) {
						this.controllOccurrence(compensManage, item, overTime);
					}
					// Work time
					if (item.getOccurrenceType() == CompensatoryOccurrenceDivision.WorkDayOffTime.value) {
						this.controllOccurrence(compensManage, item, workTime);
					}
				}
			}
		}
		CompensatoryLeaveComSetting clcs = command.toDomain(companyId);
		if (findClsc == null) {
			compensLeaveComSetRepository.insert(clcs);
		} else {
			compensLeaveComSetRepository.update(clcs);
		}
	}
	
	public void controllOccurrence(boolean compensManage, CompensatoryOccurrenceSettingDto commandOccurrence,
			CompensatoryOccurrenceSetting findOccurrence) {
		// for certain time
		if (commandOccurrence.getTransferSetting().getTransferDivision() != TransferSettingDivision.CertainTime.value
				|| !commandOccurrence.getTransferSetting().isUseDivision() || compensManage) {
			commandOccurrence.getTransferSetting()
					.setCertainTime(findOccurrence.getTransferSetting().getCertainTime().v().intValue());
		}
		// for half and one day time
		if (commandOccurrence.getTransferSetting().getTransferDivision() != TransferSettingDivision.DesignTime.value
				|| !commandOccurrence.getTransferSetting().isUseDivision()||compensManage) {
			commandOccurrence.getTransferSetting()
					.setHalfDayTime(findOccurrence.getTransferSetting().getHalfDayTime().v().intValue());
			commandOccurrence.getTransferSetting()
					.setOneDayTime(findOccurrence.getTransferSetting().getOneDayTime().v().intValue());
		}
		// for useDivision
		if (compensManage) {
			commandOccurrence.getTransferSetting().setUseDivision(findOccurrence.getTransferSetting().isUseDivision());
		}

		// for transferSetting Division
		if (compensManage || !commandOccurrence.getTransferSetting().isUseDivision()) {
			commandOccurrence.getTransferSetting()
					.setTransferDivision(findOccurrence.getTransferSetting().getTransferDivision().value);
		}
	}

}
