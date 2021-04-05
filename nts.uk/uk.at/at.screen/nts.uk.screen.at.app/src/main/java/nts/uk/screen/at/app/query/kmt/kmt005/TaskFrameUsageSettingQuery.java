package nts.uk.screen.at.app.query.kmt.kmt005;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationMethod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.operationsettings.TaskOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.operationsettings.TaskOperationSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskframe.TaskFrameUsageSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TaskFrameUsageSettingQuery {
    @Inject
    private TaskOperationSettingRepository operationSettingRepo;

    @Inject
    private TaskFrameUsageSettingRepository repo;

    public List<TaskFrameSettingDto> get() {
        String companyId = AppContexts.user().companyId();
        Optional<TaskOperationSetting> operationSetting = operationSettingRepo.getTasksOperationSetting(companyId);
        if (!operationSetting.isPresent() || operationSetting.get().getTaskOperationMethod() == TaskOperationMethod.DO_NOT_USE)
            throw new BusinessException("Msg_2122");
        if (operationSetting.get().getTaskOperationMethod() == TaskOperationMethod.USE_ON_SCHEDULE)
            throw new BusinessException("Msg_2114", "KMT005_1");
        TaskFrameUsageSetting setting = repo.getWorkFrameUsageSetting(companyId);
        if (setting == null) {
            return Collections.emptyList();
        } else {
            return setting.getFrameSettingList()
                    .stream()
                    .map(s -> new TaskFrameSettingDto(
                            s.getTaskFrameNo().v(),
                            s.getTaskFrameName().v(),
                            s.getUseAtr().value
                    )).collect(Collectors.toList());
        }
    }
}
