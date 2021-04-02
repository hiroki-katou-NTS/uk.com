package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *  作業情報を削除する
 */

@Stateless
@Transactional
public class DeleteWorkInformationCommandHandler extends CommandHandler<WorkInformationCommand> {

    @Inject
    TaskingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<WorkInformationCommand> commandHandlerContext) {
        String cid = AppContexts.user().companyId();
        WorkInformationCommand command = commandHandlerContext.getCommand();
        TaskCode code = new TaskCode(command.getCode());
        TaskFrameNo frameNo = new TaskFrameNo(command.getTaskFrameNo());
        repository.delete(cid, frameNo, code);
    }
}

