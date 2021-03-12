package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;


import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingworkplace.NarrowingByWorkplaceRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 職場別作業情報を変更する
 */
@Stateless
public class ChangeWorkInfoByWorkplaceCommandHandler extends CommandHandler<ChangeWorkInfoByWorkplaceCommand> {
    @Inject
    private NarrowingByWorkplaceRepository narrowingRepository;

    @Inject
    private TaskingRepository taskingRepository;

    @Override
    protected void handle(CommandHandlerContext<ChangeWorkInfoByWorkplaceCommand> commandHandlerContext) {
        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        Map<Integer, List<String>> mapFrameAndCode = command.getMapFrameAndCode();
        for (val e : mapFrameAndCode.keySet()) {
            val item = mapFrameAndCode.getOrDefault(e, Collections.emptyList());
            List<TaskCode> taskCodeList = item.stream().map(TaskCode::new).collect(Collectors.toList());
            val taskFrameNo = new TaskFrameNo(e);
            val wplId = command.getWorkplaceId();
            if (item.isEmpty()) {
                narrowingRepository.delete(cid,wplId, taskFrameNo);
            } else {
                RequireImpl require = new RequireImpl(taskingRepository);
                val optNarrow = narrowingRepository.getOptionalWork(wplId, taskFrameNo);
                if (optNarrow.isPresent()) {
                    val nr = optNarrow.get();
                    nr.changeCodeList(require, taskCodeList);
                    narrowingRepository.update(nr);
                } else {
                    val nr = NarrowingDownTaskByWorkplace.create(require, wplId, taskFrameNo, taskCodeList);
                    narrowingRepository.insert(nr);
                }
            }
        }
    }

    @AllArgsConstructor
    public class RequireImpl implements NarrowingDownTaskByWorkplace.Require {
        private TaskingRepository taskingRepository;

        @Override
        public List<Task> getTask(String cid, TaskFrameNo taskFrameNo) {
            return taskingRepository.getListTask(cid, taskFrameNo);
        }
    }
}
