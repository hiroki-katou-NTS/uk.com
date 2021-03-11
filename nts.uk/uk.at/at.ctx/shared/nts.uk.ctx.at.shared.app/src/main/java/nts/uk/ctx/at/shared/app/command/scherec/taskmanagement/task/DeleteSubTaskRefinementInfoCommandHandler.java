package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 下位作業絞込情報を削除する
 */
@Stateless
public class DeleteSubTaskRefinementInfoCommandHandler extends CommandHandler<DeleteSubTaskRefinementInfoCommand>{

    @Inject
    private TaskingRepository taskingRepository;
    @Override
    protected void handle(CommandHandlerContext<DeleteSubTaskRefinementInfoCommand> commandHandlerContext) {

        val command = commandHandlerContext.getCommand();
        val cid = AppContexts.user().companyId();
        TaskFrameNo taskFrameNo = new TaskFrameNo(command.getTaskFrameNo());
        TaskCode taskCode = new TaskCode(command.getParentWorkCode());
        val optionalTask = taskingRepository.getOptionalTask(cid,taskFrameNo,taskCode);
        if(!optionalTask.isPresent()){
            throw new BusinessException("Msg_37");
        }else {
            taskingRepository.delete(cid,taskFrameNo,taskCode);
        }
    }
}
