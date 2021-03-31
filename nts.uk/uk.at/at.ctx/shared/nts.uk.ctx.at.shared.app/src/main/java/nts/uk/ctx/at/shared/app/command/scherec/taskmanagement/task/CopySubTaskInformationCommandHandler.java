package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.CopyChildTaskSettingDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 下位作業情報を複写する
 */
@Stateless
@Transactional
public class CopySubTaskInformationCommandHandler extends CommandHandler<CopySubTaskInformationCommand> {
    @Inject
    private TaskingRepository taskingRepository;

    @Override
    protected void handle(CommandHandlerContext<CopySubTaskInformationCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        TaskFrameNo taskFrameNo = new TaskFrameNo(command.getTaskFrameNo());
        TaskCode copySource = new TaskCode(command.getCopySource());
        List<TaskCode> copyDestinationList = command.getCopyDestinationList()
                .stream().map(TaskCode::new).collect(Collectors.toList());
        RequireImpl require = new RequireImpl(taskingRepository);

        AtomTask atomTask = CopyChildTaskSettingDomainService.doCopy(require, taskFrameNo, copySource, copyDestinationList);
        transaction.execute(atomTask);
    }

    @AllArgsConstructor
    public class RequireImpl implements CopyChildTaskSettingDomainService.Require {
        private TaskingRepository taskingRepository;

        @Override
        public Optional<Task> getOptionalTask(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
            return taskingRepository.getOptionalTask(cid, taskFrameNo, code);
        }

        @Override
        public List<Task> getListTask(String cid, TaskFrameNo taskFrameNo, List<TaskCode> codes) {
            return taskingRepository.getListTask(cid, taskFrameNo, codes);
        }

        @Override
        public void update(Task task) {
            taskingRepository.update(task);
        }

        @Override
        public List<Task> getTask(String cid, TaskFrameNo taskFrameNo) {
            return taskingRepository.getListTask(cid, taskFrameNo);
        }
    }
}
