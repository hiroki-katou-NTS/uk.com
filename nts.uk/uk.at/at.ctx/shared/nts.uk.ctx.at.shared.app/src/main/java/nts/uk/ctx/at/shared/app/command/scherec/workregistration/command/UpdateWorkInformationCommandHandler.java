package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;

/**
 * 作業情報を変更する
 */

import lombok.AllArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@Transactional
public class UpdateWorkInformationCommandHandler extends CommandHandler<WorkInformationCommand> {

    @Inject
    TaskingRepository repository;

    @Override
    protected void handle(CommandHandlerContext<WorkInformationCommand> commandHandlerContext) {
        WorkInformationCommand command = commandHandlerContext.getCommand();
        String cid = AppContexts.user().companyId();
        TaskCode code = new TaskCode(command.getCode());
        TaskFrameNo frameNo = new TaskFrameNo(command.getTaskFrameNo());
        Optional<Task> taskOldOpt = repository.getOptionalTask(cid, frameNo, code);
        if(!taskOldOpt.isPresent()) {
            throw new BusinessException("Msg_3");
        }
        else {
            RequireImpl require = new RequireImpl(repository);
            Task task = Task.create(
                    require,
                    frameNo,
                    code,
                    new DatePeriod(command.getStartDate(),command.getEndDate()),
                    new ExternalCooperationInfo(
                            Optional.ofNullable(StringUtils.isEmpty(command.getCooperationInfo().getExternalCode1()) ? null : new TaskExternalCode(command.getCooperationInfo().getExternalCode1())),
                            Optional.ofNullable(StringUtils.isEmpty(command.getCooperationInfo().getExternalCode2()) ? null : new TaskExternalCode(command.getCooperationInfo().getExternalCode2())),
                            Optional.ofNullable(StringUtils.isEmpty(command.getCooperationInfo().getExternalCode3()) ? null : new TaskExternalCode(command.getCooperationInfo().getExternalCode3())),
                            Optional.ofNullable(StringUtils.isEmpty(command.getCooperationInfo().getExternalCode4()) ? null : new TaskExternalCode(command.getCooperationInfo().getExternalCode4())),
                            Optional.ofNullable(StringUtils.isEmpty(command.getCooperationInfo().getExternalCode5()) ? null : new TaskExternalCode(command.getCooperationInfo().getExternalCode5()))
                    ),
                    new TaskDisplayInfo(
                            new TaskName(command.getDisplayInfo().getTaskName()),
                            new TaskAbName(command.getDisplayInfo().getTaskAbName()),
                            Optional.ofNullable(StringUtils.isEmpty(command.getDisplayInfo().getColor()) ? null : new ColorCode(command.getDisplayInfo().getColor())),
                            Optional.ofNullable(StringUtils.isEmpty(command.getDisplayInfo().getTaskNote()) ? null : new TaskNote(command.getDisplayInfo().getTaskNote()))
                    ),
                    command.getChildTaskList().stream().map(TaskCode::new).collect(Collectors.toList())
            );
            repository.update(task);
        }
    }
    @AllArgsConstructor
    public class RequireImpl implements Task.Require{
        private TaskingRepository taskingRepository;
        @Override
        public List<Task> getTask(String cid, TaskFrameNo taskFrameNo) {
            return taskingRepository.getListTask(cid,taskFrameNo);
        }
    }
}
