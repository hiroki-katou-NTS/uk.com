package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.service.WorkMonthlySettingService;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 月間パターンを登録する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class MonthlyPatternRegisterCommandHandler extends CommandHandler<MonthlyPatternRegisterCommand> {

    @Inject
    private WorkMonthlySettingRepository workMonthlySettingRepository;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    @Inject
    private WorkTimeSettingService workTimeSettingService;

    @Override
    protected void handle(CommandHandlerContext<MonthlyPatternRegisterCommand> commandHandlerContext) {
        MonthlyPatternRegisterCommand command = commandHandlerContext.getCommand();
        RequireImpl require = new RequireImpl(basicScheduleService, workTypeRepo, workTimeSettingRepository,
                workTimeSettingService, workMonthlySettingRepository);
        // 登録する(Require, 月間パターンの勤務情報, boolean)
        command.getWorkMonthlySetting().forEach((item) -> {
            Optional<AtomTask> persist = WorkMonthlySettingService.register(require,
            		AppContexts.user().companyId(), command.toDomain(item), command.isOverWrite());
            persist.ifPresent(atomTask -> transaction.execute(atomTask::run));
        }
        );
    }

    @AllArgsConstructor
    private static class RequireImpl implements WorkMonthlySettingService.Require{

        private final String companyId = AppContexts.user().companyId();

        private BasicScheduleService service;

        private WorkTypeRepository workTypeRepo;

        private WorkTimeSettingRepository workTimeSettingRepository;

        private WorkTimeSettingService workTimeSettingService;

        private WorkMonthlySettingRepository workMonthlySettingRepository;

//        @Override
//        public boolean exists(String companyId, String monthlyPatternCode, GeneralDate generalDate) {
//            return workMonthlySettingRepository.exists(companyId, monthlyPatternCode, generalDate);
//        }

		@Override
		public boolean checkRegister(String companyId, String monthlyPatternCode, GeneralDate generalDate) {
            return workMonthlySettingRepository.exists(companyId, monthlyPatternCode, generalDate);
		}

		@Override
        public void add(WorkMonthlySetting workMonthlySetting) {
            workMonthlySettingRepository.add(workMonthlySetting);
        }

        @Override
        public void update(WorkMonthlySetting workMonthlySetting) {
            workMonthlySettingRepository.update(workMonthlySetting);
        }


        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return service.checkNeededOfWorkTimeSetting(workTypeCode);
        }

		@Override
		public Optional<WorkType> workType(String companyId, WorkTypeCode workTypeCode) {
			return workTypeRepo.findByPK(companyId, workTypeCode.v());
		}

		@Override
		public Optional<WorkTimeSetting> workTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			return workTimeSettingRepository.findByCode(companyId, workTimeCode.v());
		}

     // fix bug 113211
//        @Override
//        public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
//            return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
//        }

		@Override
		public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return Optional.empty();
		}

		@Override
		public Optional<FlowWorkSetting> flowWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return Optional.empty();
		}

		@Override
		public Optional<FlexWorkSetting> flexWorkSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return Optional.empty();
		}

		@Override
		public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, WorkTimeCode workTimeCode) {
			// TODO 自動生成されたメソッド・スタブ
			return Optional.empty();
		}
    }
}
