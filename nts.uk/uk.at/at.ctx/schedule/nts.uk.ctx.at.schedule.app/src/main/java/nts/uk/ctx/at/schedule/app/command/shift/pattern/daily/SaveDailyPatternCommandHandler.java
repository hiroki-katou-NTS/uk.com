/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.daily;

import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.DailyPatternRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveDailyPatternCommandHandler.
 */
@Stateless
public class SaveDailyPatternCommandHandler extends CommandHandler<DailyPatternCommand> {

	/** The daily pattern repo. */
	@Inject
	private DailyPatternRepository dailyPatternRepo;

	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;

	/** The monthly pattern repository. */
	@Inject
	private WorkTimeSettingRepository workTimeRepository;

	/** The monthly pattern repository. */
	@Inject
	private WorkTypeRepository workTypeRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DailyPatternCommand> context) {
		String companyId = AppContexts.user().companyId();
		DailyPatternCommand command = context.getCommand();
		String patternCd = command.getPatternCode();

		Optional<WorkCycle> result = this.dailyPatternRepo.getByCidAndCode(companyId, patternCd);

		// Check duplicate code in new mode.
		if (!command.getIsEditting() && result.isPresent()) {
			// validate eap and find messegeId.
			throw new BusinessException("Msg_3");
		}

		command.getDailyPatternVals().stream().filter(Objects::nonNull).forEach(item -> {
			// check pair work days
			if (!StringUtil.isNullOrEmpty(item.getWorkTypeSetCd(), true)) {

				// check setting work type
				Optional<WorkType> worktype = this.workTypeRepository.findByPK(companyId,
						item.getWorkTypeSetCd());

				// not exist data
				if (!worktype.isPresent()) {
					throw new BusinessException("Msg_389");
				}

				// not use
				if (worktype.get()
						.getDeprecate().value == DeprecateClassification.Deprecated.value) {
					throw new BusinessException("Msg_416");
				}

				// check setting work time
				if (!StringUtil.isNullOrEmpty(item.getWorkingHoursCd(), true)) {
					Optional<WorkTimeSetting> worktime = this.workTimeRepository.findByCode(companyId,
							item.getWorkingHoursCd());

					// not exist data
					if (!worktime.isPresent()) {
						throw new BusinessException("Msg_390");
					}

					// not use
					if (worktime.get().getAbolishAtr().value == AbolishAtr.ABOLISH.value) {
						throw new BusinessException("Msg_417");
					}
				}

				// Check pair
				basicScheduleService.checkPairWorkTypeWorkTime(item.getWorkTypeSetCd(),
						item.getWorkingHoursCd());

			}

		});

		WorkCycle dailyPattern = command.toDomain(companyId);

		// check add or update
		if (!result.isPresent()) {
			this.dailyPatternRepo.add(dailyPattern);
		} else {
			this.dailyPatternRepo.update(dailyPattern);
		}
	}

}
