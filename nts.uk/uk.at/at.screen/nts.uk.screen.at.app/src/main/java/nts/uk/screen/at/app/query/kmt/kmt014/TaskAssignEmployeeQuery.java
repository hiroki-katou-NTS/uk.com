package nts.uk.screen.at.app.query.kmt.kmt014;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TaskAssignEmployeeQuery {
    @Inject
    private TaskOperationSettingRepository operationSettingRepo;

    @Inject
    private TaskingRepository taskRepo;

    public List<TaskDto> getTasks(int taskFrameNo, GeneralDate baseDate) {
        String companyId = AppContexts.user().companyId();
        Optional<TaskOperationSetting> operationSetting = operationSettingRepo.getTasksOperationSetting(companyId);
        if (!operationSetting.isPresent() || operationSetting.get().getTaskOperationMethod() == TaskOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        if (operationSetting.get().getTaskOperationMethod() == TaskOperationMethod.USE_ON_SCHEDULE)
            throw new BusinessException("Msg_2114", "KMT014_1");
        return taskRepo.getListTask(companyId, baseDate, Arrays.asList(new TaskFrameNo(taskFrameNo)))
                .stream()
                .map(task -> new TaskDto(
                        task.getCode().v(),
                        task.getDisplayInfo().getTaskName().v(),
                        task.getExpirationDate().start(),
                        task.getExpirationDate().end()
                )).collect(Collectors.toList());
    }
}
