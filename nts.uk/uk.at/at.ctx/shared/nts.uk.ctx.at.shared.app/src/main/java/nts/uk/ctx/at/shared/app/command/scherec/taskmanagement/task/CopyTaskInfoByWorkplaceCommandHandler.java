package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CopyRefinementSettingDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 職場別作業情報を複写する
 */
@Stateless
public class CopyTaskInfoByWorkplaceCommandHandler extends CommandHandler<CopyTaskInfoByWorkplaceCommand> {
    @Inject
    private NarrowingByWorkplaceRepository narrowingByWorkplaceRepository;

    @Inject
    private TaskingRepository taskingRepository;

    @Override
    protected void handle(CommandHandlerContext<CopyTaskInfoByWorkplaceCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        String companyId = AppContexts.user().companyId();
        RequireImpl require = new RequireImpl(narrowingByWorkplaceRepository, taskingRepository);
        List<AtomTask> deleteTasks = command.getCopyDestinationWplId().stream()
                .map(wkpId -> AtomTask.of(() -> narrowingByWorkplaceRepository.delete(companyId, wkpId)))
                .collect(Collectors.toList());
        List<AtomTask> copyTasks = command.getCopyDestinationWplId().stream()
                .map(wkpId -> CopyRefinementSettingDomainService.doCopy(require, command.getCopySourceWplId(), wkpId))
                .collect(Collectors.toList());
        transaction.execute(AtomTask.bundle(deleteTasks).then(AtomTask.bundle(copyTasks)));
    }

    @AllArgsConstructor
    public class RequireImpl implements CopyRefinementSettingDomainService.Require {

        private NarrowingByWorkplaceRepository narrowingByWorkplaceRepository;

        private TaskingRepository taskingRepository;

        @Override
        public List<NarrowingDownTaskByWorkplace> getListWorkByWpl(String workPlaceId) {
            return narrowingByWorkplaceRepository.getListWorkByWpl(AppContexts.user().companyId(), workPlaceId);
        }

        @Override
        public void insert(NarrowingDownTaskByWorkplace narrowing) {
            narrowingByWorkplaceRepository.insert(narrowing);
        }

        @Override
        public void delete(String workPlaceId) {
            narrowingByWorkplaceRepository.delete(AppContexts.user().companyId(), workPlaceId);
        }

        @Override
        public List<Task> getTask(String cid, TaskFrameNo taskFrameNo) {
            return taskingRepository.getListTask(cid, taskFrameNo);
        }
    }
}
