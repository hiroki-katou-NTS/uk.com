package nts.uk.ctx.at.schedule.app.command.shift.workcycle;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.command.AddWorkCycleCommand;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleDtlRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RegisterWorkCycleService;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.WorkCycleCreateResult;
import nts.uk.ctx.at.shared.dom.WorkInformation;
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

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 勤務サイクルを更新登録する
 */
@Stateless
public class UpdateWorkCycleCommandHandler extends CommandHandlerWithResult<AddWorkCycleCommand, WorkCycleCreateResult> {

    @Inject
    WorkCycleRepository workCycleRepository;

    @Inject
    WorkCycleDtlRepository workCycleDtlRepository;

    @Inject
    private BasicScheduleService basicScheduleService;

    @Inject
    private WorkTypeRepository workTypeRepo;

    @Inject
    private WorkTimeSettingRepository workTimeSettingRepository;

    @Inject
    private WorkTimeSettingService workTimeSettingService;

    @Override
    protected WorkCycleCreateResult handle(CommandHandlerContext<AddWorkCycleCommand> context) {
        AddWorkCycleCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        RegisterWorkCycleServiceImlp require = new RegisterWorkCycleServiceImlp(workCycleRepository, workCycleDtlRepository);
        WorkInformation.Require workRequired = new WorkInfoRequireImpl(basicScheduleService, workTypeRepo,workTimeSettingRepository,workTimeSettingService, basicScheduleService);
        WorkCycleCreateResult result = RegisterWorkCycleService.register(workRequired, require, AddWorkCycleCommand.createFromCommand(command, cid), false);
        if (result.getErrorStatusList().isEmpty()) {
            AtomTask atomTask = result.getAtomTask().get();
            transaction.execute(() ->{
                atomTask.run();
            });
        }
        return result;
    }

    @AllArgsConstructor
    private class RegisterWorkCycleServiceImlp implements RegisterWorkCycleService.Require {

        @Inject
        WorkCycleRepository workCycleRepository;

        @Inject
        WorkCycleDtlRepository workCycleDtlRepository;

        @Override
        public boolean exists(String cid, String code) {
            return workCycleRepository.exists(cid, code);
        }

        @Override
        public void insert(WorkCycle item) {
            this.workCycleDtlRepository.add(item);
            this.workCycleDtlRepository.add(item);
        }

        @Override
        public void update(WorkCycle item) {
            this.workCycleRepository.update(item);
            this.workCycleDtlRepository.update(item);
        }
    }

    @AllArgsConstructor
    private static class WorkInfoRequireImpl implements WorkInformation.Require {

        private final String companyId = AppContexts.user().companyId();

        @Inject
        private BasicScheduleService service;

        @Inject
        private WorkTypeRepository workTypeRepo;

        @Inject
        private WorkTimeSettingRepository workTimeSettingRepository;

        @Inject
        private WorkTimeSettingService workTimeSettingService;

        @Inject
        private BasicScheduleService basicScheduleService;

        @Override
        public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
            return service.checkNeededOfWorkTimeSetting(workTypeCode);
        }

        @Override
        public Optional<WorkType> findByPK(String workTypeCd) {
            return workTypeRepo.findByPK(companyId, workTypeCd);
        }

        @Override
        public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
            return workTimeSettingRepository.findByCode(companyId, workTimeCode);
        }

        @Override
        public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd,
                                                                   String workTypeCd, Integer workNo) {
            return workTimeSettingService .getPredeterminedTimezone(companyId, workTimeCd, workTypeCd, workNo);
        }

        @Override
        public WorkStyle checkWorkDay(String workTypeCode) {
            return basicScheduleService.checkWorkDay(workTypeCode);
        }
    }
}
