package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.taskassign.taskassignemployee;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskassign.taskassingemployee.TaskAssignEmployeeRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskassign.taskassignemployee.TaskAssignEmployee;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RegisterTaskAssignEmployeeCommandHandler extends CommandHandler<TaskAssignEmployeeCommand> {
    @Inject
    private TaskAssignEmployeeRepository repository;

    @Override
    protected void handle(CommandHandlerContext<TaskAssignEmployeeCommand> commandHandlerContext) {
        String companyId = AppContexts.user().companyId();
        TaskAssignEmployeeCommand command = commandHandlerContext.getCommand();
        repository.delete(companyId, command.getTaskFrameNo(), command.getTaskCode());
        command.getEmployeeIds().forEach(empId -> {
            repository.insert(new TaskAssignEmployee(
                    empId,
                    command.getTaskFrameNo(),
                    command.getTaskCode()
            ));
        });
    }
}
