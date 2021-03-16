package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;


import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignworkplace.NarrowingDownTaskByWorkplace;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/*
    作業情報を新規に登録する
*/

@Stateless
public class RegisterWorkInformationCommandHandler extends CommandHandler<RegisterWorkInformationCommand> {
    @Inject
    private TaskOperationSettingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<RegisterWorkInformationCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        RegisterWorkInformationCommand command = commandHandlerContext.getCommand();
        val optionalTask = repository.getTasksOperationSetting(companyId);
        if(optionalTask.isPresent()){
            throw new BusinessException("Msg_3");
        } else {
            repository.insert(new NarrowingDownTaskByWorkplace(
                    companyId,
                    command.getTaskFrameNo(),
                    command.getTaskCode(),
            ));
        }
    }
}
