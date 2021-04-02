package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice.ChangeChildTaskLinkedDomainService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 下位作業絞込情報を更新する
 */
@Stateless
public class UpdateSubTaskRefinementInfoCommandHandler extends CommandHandler<UpdateSubTaskRefinementInfoCommand> {

    @Inject
    private TaskFrameUsageSettingRepository taskFrameUsageSettingRepository;

    @Inject
    private TaskingRepository taskingRepository;

    @Override
    protected void handle(CommandHandlerContext<UpdateSubTaskRefinementInfoCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        TaskFrameNo taskFrameNo = new TaskFrameNo(command.getTaskFrameNo());
        TaskCode taskCode = new TaskCode(command.getParentWorkCode());
        List<TaskCode> taskCodeList = command.getChildWorkList().stream().map(TaskCode::new).collect(Collectors.toList());
        RequireImpl require = new RequireImpl(taskFrameUsageSettingRepository, taskingRepository);
        AtomTask atomTask = ChangeChildTaskLinkedDomainService.change(require, taskFrameNo, taskCode, taskCodeList);
        transaction.execute(atomTask);
    }

    @AllArgsConstructor
    public class RequireImpl implements ChangeChildTaskLinkedDomainService.Require {

        private TaskFrameUsageSettingRepository usageSettingRepository;
        private TaskingRepository taskingRepository;

        @Override
        public TaskFrameUsageSetting getWorkFrameUsageSetting(String cid) {
            return usageSettingRepository.getWorkFrameUsageSetting(cid);
        }

        @Override
        public Optional<Task> getOptionalWork(String cid, TaskFrameNo taskFrameNo, TaskCode code) {
            return taskingRepository.getOptionalTask(cid, taskFrameNo, code);
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
