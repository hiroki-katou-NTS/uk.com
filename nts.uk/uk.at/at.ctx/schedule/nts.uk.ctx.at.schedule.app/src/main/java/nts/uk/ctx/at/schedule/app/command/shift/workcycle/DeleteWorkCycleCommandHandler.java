package nts.uk.ctx.at.schedule.app.command.shift.workcycle;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.app.command.shift.workcycle.command.DeleteWorkCycleCommand;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleDtlRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleRepository;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.DeleteWorkCycleService;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * 勤務サイクルを削除する
 */
@Stateless
public class DeleteWorkCycleCommandHandler extends CommandHandler<DeleteWorkCycleCommand> {

    @Inject
    WorkCycleRepository workCycleRepository;

    @Inject
    WorkCycleDtlRepository workCycleDtlRepository;


    @Override
    protected void handle(CommandHandlerContext<DeleteWorkCycleCommand> context) {
        DeleteWorkCycleCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();
        DeleteWorkCycleImpl require = new DeleteWorkCycleImpl(workCycleRepository, workCycleDtlRepository);
        DeleteWorkCycleService.delete(require, cid, command.getWorkCycleCode());
    }

    @AllArgsConstructor
    private class DeleteWorkCycleImpl implements DeleteWorkCycleService.Require {

        @Inject
        WorkCycleRepository workCycleRepository;

        @Inject
        WorkCycleDtlRepository workCycleDtlRepository;

        @Override
        public Optional<WorkCycle> getWorkingCycle(String cid, String code) {
            return workCycleRepository.getByCidAndCode(cid, code);
        }

        @Override
        public List<WorkCycleInfo> getWorkingCycleInfos(String cid, String code) {
            return workCycleDtlRepository.getByCode(cid, code);
        }

        @Override
        public void deleteWorkCycle(String cid, String code) {
            workCycleRepository.delete(cid, code);
        }

        @Override
        public void deleteWorkCycleInfo(String cid, String code, int dispOrder) {
            workCycleDtlRepository.delete(cid, code, dispOrder);
        }
    }
}
