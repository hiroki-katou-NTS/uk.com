/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ac.vacation.setting;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.sys.assist.pub.command.mastercopy.GlobalCopyItemEnum;
import nts.uk.ctx.sys.assist.pub.command.mastercopy.GlobalMasterDataCopyEvent;
import nts.uk.ctx.sys.assist.pub.command.mastercopy.MasterDataCopyEventSubscriber;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class VacationMasterDataCopyEventSubscriber.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class VacationMasterDataCopyEventSubscriber extends MasterDataCopyEventSubscriber {

	/** The com 60 hour vacation repo. */
	@Inject
	private Com60HourVacationRepository com60HourVacationRepo;

	@Inject
	private ComSubstVacationRepository comSubstVacationRepo;

	@Inject
	private CompensLeaveComSetRepository compensatoryLeaveComSettingRepo;

	@Inject
	private RetentionYearlySettingRepository retentionYearlySettingRepo;

	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.dom.event.DomainEventSubscriber#handle(nts.arc.layer.dom.
	 * event.DomainEvent)
	 */
	@Override
	public void handle(GlobalMasterDataCopyEvent event) {
		String sourceCid = AppContexts.user().zeroCompanyIdInContract();
		String targetCid = event.getCompanyId();
		String taskId = event.getTaskId();

		// Copy Com60HourVacation
		event.getCopyTargetItem(GlobalCopyItemEnum.Com60HourVacation).ifPresent(item -> {
			switch (item.getProcessMethod()) {
			case ADD_NEW:
				// Add new : gọi ra thuật toán add new
				this.copyDataWithLog(taskId, item,
						() -> com60HourVacationRepo.copyMasterData(sourceCid, targetCid, false));
				break;

			case REPLACE_ALL:
				// Replace all
				this.copyDataWithLog(taskId, item,
						() -> com60HourVacationRepo.copyMasterData(sourceCid, targetCid, true));
				break;

			default:
				// DO_NOTHING
				break;
			}
		});

		// Copy ComSubstVacation
		event.getCopyTargetItem(GlobalCopyItemEnum.ComSubstVacation).ifPresent(item -> {
			switch (item.getProcessMethod()) {
			case ADD_NEW:
				// Add new : gọi ra thuật toán add new
				this.copyDataWithLog(taskId, item,
						() -> comSubstVacationRepo.copyMasterData(sourceCid, targetCid, false));
				break;

			case REPLACE_ALL:
				// Replace all
				this.copyDataWithLog(taskId, item,
						() -> comSubstVacationRepo.copyMasterData(sourceCid, targetCid, true));
				break;

			default:
				// DO_NOTHING
				break;
			}
		});

		// Copy CompensatoryLeaveComSetting
		event.getCopyTargetItem(GlobalCopyItemEnum.CompensatoryLeaveComSetting).ifPresent(item -> {
			switch (item.getProcessMethod()) {
			case ADD_NEW:
				// Add new : gọi ra thuật toán add new
				this.copyDataWithLog(taskId, item, () -> compensatoryLeaveComSettingRepo
						.copyMasterData(sourceCid, targetCid, false));
				break;

			case REPLACE_ALL:
				// Replace all
				this.copyDataWithLog(taskId, item, () -> compensatoryLeaveComSettingRepo
						.copyMasterData(sourceCid, targetCid, true));
				break;

			default:
				// DO_NOTHING
				break;
			}
		});

		// Copy RetentionYearlySetting
		event.getCopyTargetItem(GlobalCopyItemEnum.RetentionYearlySetting).ifPresent(item -> {
			switch (item.getProcessMethod()) {
			case ADD_NEW:
				// Add new : gọi ra thuật toán add new
				this.copyDataWithLog(taskId, item, () -> retentionYearlySettingRepo
						.copyMasterData(sourceCid, targetCid, false));
				break;

			case REPLACE_ALL:
				// Replace all
				this.copyDataWithLog(taskId, item, () -> retentionYearlySettingRepo
						.copyMasterData(sourceCid, targetCid, true));
				break;

			default:
				// DO_NOTHING
				break;
			}
		});

		// Copy AnnualPaidLeaveSetting
		event.getCopyTargetItem(GlobalCopyItemEnum.AnnualPaidLeaveSetting).ifPresent(item -> {
			switch (item.getProcessMethod()) {
			case ADD_NEW:
				// Add new : gọi ra thuật toán add new
				this.copyDataWithLog(taskId, item, () -> annualPaidLeaveSettingRepo
						.copyMasterData(sourceCid, targetCid, false));
				break;

			case REPLACE_ALL:
				// Replace all
				this.copyDataWithLog(taskId, item, () -> annualPaidLeaveSettingRepo
						.copyMasterData(sourceCid, targetCid, true));
				break;

			default:
				// DO_NOTHING
				break;
			}
		});

	}

}