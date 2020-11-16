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
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
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
                workTimeSettingService, basicScheduleService,workMonthlySettingRepository);
        // 登録する(Require, 月間パターンの勤務情報, boolean)
        command.getWorkMonthlySetting().forEach((item) -> {
            Optional<AtomTask> persist = WorkMonthlySettingService.register(require, command.toDomain(item),
                    command.isOverWrite());
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

        private BasicScheduleService basicScheduleService;

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
        public Optional<WorkType> getWorkType(String workTypeCd) {
            return workTypeRepo.findByPK(companyId, workTypeCd);
        }

        @Override
        public Optional<WorkTimeSetting> getWorkTime(String workTimeCode) {
            return workTimeSettingRepository.findByCode(companyId, workTimeCode);
        }

        @Override
        public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTypeCd, String workTimeCd, Integer workNo) {
            return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
        }

        @Override
        public WorkStyle checkWorkDay(String workTypeCode) {
            return basicScheduleService.checkWorkDay(workTypeCode);
        }
    }
}
